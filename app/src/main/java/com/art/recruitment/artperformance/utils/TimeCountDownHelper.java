package com.art.recruitment.artperformance.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


//TimeCountDownUtil.
//                builder().
//                timeSpan(60).   //计时60秒
//                countInterval(1000).  //两次计时间隔1000，由于单位为TimeUnit.MILLISECONDS，所以间隔是1S
//                timeUnit(TimeUnit.MILLISECONDS). //两次计时间隔单位
//                listener(new TimeCountDownUtil.TimeCountListener() {
//                      @Override
//                      public void onTimeCountDown(long time) {
//
//                      }
//               }).
//               start();
//    结果：60  59  58  ........1  0   不会输出小于0的值

public class TimeCountDownHelper {

    private long mTimeSpan;  //时间跨度,即计时的时间范围
    private long mCountInterval;  //计时时间间隔
    private TimeUnit mTimeUnit;  //时间间隔单位
    private TimeCountListener mTimeCountListener;  //计时时间监听。
    private Disposable mDisposable;

    /**
     * 倒计时监听
     */
    public interface TimeCountListener {

        void onTimeCountDown(long time);
    }

    /**
     * 倒计时时间跨度
     *
     * @param timeSpan
     * @return
     */
    public TimeCountDownHelper timeSpan(long timeSpan) {
        mTimeSpan = timeSpan;
        return this;
    }

    /**
     * 两次计时的时间间隔
     *
     * @param interval
     * @return
     */
    public TimeCountDownHelper countInterval(long interval) {
        mCountInterval = interval;
        return this;
    }

    /**
     * 计时时间间隔的单位
     *
     * @param timeUnit
     * @return
     */
    public TimeCountDownHelper timeUnit(TimeUnit timeUnit) {
        mTimeUnit = timeUnit;
        return this;
    }

    public TimeCountDownHelper listener(TimeCountListener listener) {
        mTimeCountListener = listener;
        return this;
    }

    public void start() {

        release();

        mDisposable = Observable.
                interval(mCountInterval, mTimeUnit).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mTimeCountListener == null) {
                            throw new IllegalArgumentException("请设置TimeCountListener");
                        }

                        if (mTimeSpan - aLong < 0) {
                            release();
                            return;
                        }

                        mTimeCountListener.onTimeCountDown(mTimeSpan - aLong);

                    }
                });
    }


    public void release() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
