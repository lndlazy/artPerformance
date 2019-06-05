package com.art.recruitment.common.baserx;

import android.content.Intent;

import com.art.recruitment.common.ActivityManager;
import com.art.recruitment.common.R;
import com.art.recruitment.common.base.BaseBean;
import com.art.recruitment.common.base.ui.BaseActivity;
import com.art.recruitment.common.base.ui.BaseFragment;
import com.art.recruitment.common.http.config.RequestConfig;
import com.art.recruitment.common.http.error.ApiException;
import com.art.recruitment.common.http.error.ErrorCode;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.error.ExceptionConverter;
//import com.art.recruitment.common.view.CustomDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.facebook.stetho.common.LogUtil;

import org.json.JSONObject;

import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * des:订阅封装
 */

public abstract class RxSubscriber<R, T extends BaseBean<R>> implements Observer<R> {

    //Http 返回码401监听，返回401需要退出登录
    private static GlobalErrorListener mGlobalErrorListener;

    public BaseActivity mActivity;
    public BaseFragment mFragment;
    //    private CustomDialog mCustomDialog; //用于在dialog之上显示loadingvie
    private RequestConfig<R, T> mRequestConfig;
    private Disposable mDisposable;
    private R mOnNextData;  //onNext方法的data，成功回调
    private R mErrorData;   //服务器错误码的data，向_onError()方法传递

    private String mSuccessMessage;  //JavaBean的Message字段的信息

    public void setRequestConfig(RequestConfig<R, T> requestConfig) {
        this.mRequestConfig = requestConfig;
        mActivity = requestConfig.getPresenter().getActivity();
        mFragment = requestConfig.getPresenter().getFragment();
//        mCustomDialog = requestConfig.getTargetDialog();
    }

    /*************************************************************************************************************************/

    /**
     * 进行订阅请求网络
     *
     * @param observable
     */
    public void doSubscribe(Observable<T> observable) {
        observable.
                flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(T t) throws Exception {

                        mSuccessMessage = t.getMessage();
                        mErrorData = t.getData();
                        if (t.getCode() == ErrorCode.CODE_SERVER_SUCCESS) {  //接口返回200成功码

                            if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
                                LogUtils.d(mRequestConfig.getTag(), "-----JavaBean的Code为" + ErrorCode.CODE_SERVER_SUCCESS);
                            }

                            return Observable.just(t.getData());
                            //成功直接返回数据

                        }
//                        else if (t.getCode() == ErrorCode.CODE_UNAUTHORIZED) {
//
////                            //TODO 401 重新登录
//
//                            if (mActivity!=null) {
//                                Intent intent = new Intent("com.art.recruitment.artperformance.LOGIN_IN");
//                                intent.addCategory("com.art.recruitment.artperformance.LOGIN_IN");
//                                mActivity.startActivity(intent);
//                            }
//
//                            return null;
//                            throw new ApiException(t.getCode(), ErrorType.ERROR_API, t.getMessage(), mThrowable);
//                        }
                        else {
                            if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
                                LogUtils.d(mRequestConfig.getTag(), "-----JavaBean的Code为" + t.getCode());
                            }

                            Throwable mThrowable = new Throwable("接口返回了错误业务码-----" + t.getCode());

                            throw new ApiException(t.getCode(), ErrorType.ERROR_API, t.getMessage(), mThrowable);
                        }

                    }
                }).
                subscribeOn(Schedulers.io()).
                unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                onErrorResumeNext(new Function<Throwable, ObservableSource<? extends R>>() {
                    @Override
                    public ObservableSource<? extends R> apply(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());

                        try {
                            ApiException apiException = ExceptionConverter.convertException(throwable);
                            if (apiException.getCode() == ErrorCode.CODE_UNAUTHORIZED) {
                                if (mActivity != null) {
                                    ActivityManager.getInstance().finishAllActivity();
                                    LogUtils.d("重新登录=====>>");
                                    Intent intent = new Intent("com.art.recruitment.artperformance.LOGIN_IN");
                                    intent.addCategory("com.art.recruitment.artperformance.LOGIN_IN");
                                    mActivity.startActivity(intent);
                                }
                                return Observable.error(ExceptionConverter.convertException(
                                        new ApiException(ErrorCode.CODE_UNAUTHORIZED, ErrorType.ERROR_API, "登录过期", null)));

                            }
//                            JSONObject j = new JSONObject(throwable.getMessage());
//
//                            int code = j.getInt("code");
//
//                            if (code == ErrorCode.CODE_UNAUTHORIZED) {
//                                if (mActivity!=null) {
//                                    Intent intent = new Intent("com.art.recruitment.artperformance.LOGIN_IN");
//                                    intent.addCategory("com.art.recruitment.artperformance.LOGIN_IN");
//                                    mActivity.startActivity(intent);
//                                }
//                                return Observable.error(ExceptionConverter.convertException(new Throwable("登录超时")));
//                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return Observable.error(ExceptionConverter.convertException(throwable));
                    }
                }).
                subscribe(this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

        if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
            LogUtils.d(mRequestConfig.getTag(), "-----onSubscribe()");
        }

        mDisposable = d;
        showLoadingViewIfNecessary();
    }

    @Override
    public void onNext(R r) {

        if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
            LogUtils.d(mRequestConfig.getTag(), "-----onNext()");
        }

        mOnNextData = r;
    }

    @Override
    public void onError(Throwable e) {

        LogUtils.e("LogOut---onError");
        if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
            LogUtils.d(mRequestConfig.getTag(), "-----onError()");
        }

        e.printStackTrace();

        dismissLoadingViewIfNecessary(true);

        doDispose();

        _onError(ErrorType.ERROR_UNKNOWN, ErrorCode.CODE_UNKNOWN, e.getMessage(), null);
    }

    @Override
    public void onComplete() {

        if (mRequestConfig != null && !StringUtils.isTrimEmpty(mRequestConfig.getTag())) {
            LogUtils.d(mRequestConfig.getTag(), "-----onComplete()");
        }

        doDispose();

        dismissLoadingViewIfNecessary(false);

        _onSuccess(mOnNextData, mSuccessMessage);

    }

    /**
     * 解除订阅关系
     */
    private void doDispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    /**
     * 显示加载进度指示符
     */
    private void showLoadingViewIfNecessary() {
        if (mRequestConfig != null && mRequestConfig.isShowLoading()) {
            switch (mRequestConfig.getRequestMode()) {
                case SINGLE:
                    //单网络请求显示加载进度View
                    operateLoadingViewVisibility(true);  //单网络请求显示加载进度View
                    break;

                case CHAIN:

                    switch (mRequestConfig.getChainPosition()) {
                        case CHAIN_START:
                            operateLoadingViewVisibility(true);
                            //链式请求的起始请求显示加载进度
                            break;

                        case CHAIN_MIDDLE:
                            //链式请求的中间请求不进行加载进度显示操作
                            break;

                        case CHAIN_END:
                            //链式请求的结束请求不进行加载进度显示操作
                            break;

                        default:
                            break;
                    }
            }
        }
    }


    private void dismissLoadingViewIfNecessary(boolean isError) {
        if (mRequestConfig != null && mRequestConfig.isShowLoading()) {
            switch (mRequestConfig.getRequestMode()) {
                case SINGLE:
                    operateLoadingViewVisibility(false);
                    break;

                case CHAIN:

                    switch (mRequestConfig.getChainPosition()) {
                        case CHAIN_START:
                            if (isError) {  //链式调用发生错误直接隐藏加载进度指示符
                                operateLoadingViewVisibility(false);
                            }
                            break;

                        case CHAIN_MIDDLE:
                            if (isError) {  //链式调用发生错误直接隐藏加载进度指示符
                                operateLoadingViewVisibility(false);
                            }
                            break;

                        case CHAIN_END:
                            //链式调用最后请求必须隐藏加载进度指示符
                            operateLoadingViewVisibility(false);

                            break;
                        default:

                    }
            }
        }
    }

    /**
     * 操作加载进度View的显示和隐藏
     *
     * @param isShow true:显示   false：隐藏
     */
    private void operateLoadingViewVisibility(boolean isShow) {
        if (isShow) {
            /*if (mCustomDialog != null) {
                mCustomDialog.showLoadingView();
                return;
            }*/

            if (mActivity != null) {
                mActivity.showLoadingView();
            } else if (mFragment != null) {
                mFragment.showLoadingView();
            }
        } else {

            /*if (mCustomDialog != null) {
                mCustomDialog.hideLoadingView();
                return;
            }*/

            if (mActivity != null) {
                mActivity.dismissLoadingView();
            } else {
                mFragment.dismissLoadingView();
            }
        }

    }

    /******************************************关于重新登录的逻辑模块**********************************************/
    //401返回码监听listener,需要在Application的onCreate()方法中注册
    public interface GlobalErrorListener {

        void onReturn401Code(RxSubscriber rxSubscriber, String message);

        void onReturn9105Code(RxSubscriber rxSubscriber, String message);

        void onReturn9107Code(RxSubscriber rxSubscriber, String message);

        void onReturn9108Code(RxSubscriber rxSubscriber, String message);

        void onReturn9109Code(RxSubscriber rxSubscriber, String message);
    }

    public static void registerGlobalErrorListener(GlobalErrorListener listener) {
        mGlobalErrorListener = listener;
    }

    public BaseActivity getActivity() {
        return mActivity;
    }


    public void setmActivity(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    /**************************************************************************************************************/

    public Disposable getDisposable() {
        return mDisposable;
    }

    protected abstract void _onSuccess(R r, String successMessage);


    protected abstract void _onError(ErrorType errorType, int errorCode, String message, R data);

}
