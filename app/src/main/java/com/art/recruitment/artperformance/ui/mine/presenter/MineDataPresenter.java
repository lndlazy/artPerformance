package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.mine.ConsummateInfoBean;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.artperformance.bean.mine.PathUrlBean;
import com.art.recruitment.artperformance.bean.mine.SignaTureBean;
import com.art.recruitment.artperformance.ui.mine.contract.MineDataContract;
import com.art.recruitment.common.base.BaseBean;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.orhanobut.logger.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MineDataPresenter extends BasePresenter<MineDataContract> {

    /**
     * 获取个人资料
     */
    public void getPersonalData() {

        Api.
                observable(Api.getService(MineService.class).getPersonalData()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineBean.DataBean, MineBean>() {
                    @Override
                    protected void _onSuccess(MineBean.DataBean bean, String successMessage) {
                        mView.returnMineDataBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 完善基本资料
     */
    public void consummateInfo(String codeStr) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), codeStr);

        Api.
                observable(Api.getService(MineService.class).consummateInfo(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ConsummateInfoBean.DataBean, ConsummateInfoBean>() {
                    @Override
                    protected void _onSuccess(ConsummateInfoBean.DataBean bean, String successMessage) {
                        mView.returnEssentialInfoBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ConsummateInfoBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 完善3项基本信息
     */
    public void consummateInfo3(String codeStr) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), codeStr);

        Api.
                observable(Api.getService(MineService.class).consummateInfo3(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ConsummateInfoBean.DataBean, ConsummateInfoBean>() {
                    @Override
                    protected void _onSuccess(ConsummateInfoBean.DataBean bean, String successMessage) {
                        mView.returnEssentialInfoBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ConsummateInfoBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * OSS签名
     */
    public void signaTure(String codeStr) {

        Api.
                observable(Api.getService(MineService.class).signaTure(codeStr)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<String, SignaTureBean>() {
                    @Override
                    protected void _onSuccess(String bean, String successMessage) {
                        mView.returnSignaTureBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, String bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * OSS签名信息
     */
    public void oss() {

        Api.
                observable(Api.getService(MineService.class).oss()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<OssBean.DataBean, OssBean>() {
                    @Override
                    protected void _onSuccess(OssBean.DataBean bean, String successMessage) {
                        mView.returnOssBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, OssBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * OSS转换文件地址
     */
    public void pathUrl(String path, final int type) {

        Api.
                observable(Api.getService(MineService.class).pathUrl(path)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(false).
                doRequest(new RxSubscriber<String, BaseBean<String>>() {
                    @Override
                    protected void _onSuccess(String picUrl, String successMessage) {

                        //头像url获取成功
//                        Logger.d("bean ===> " + picUrl);
                        mView.returnPathUrlBean(picUrl, type);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, String bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

}
