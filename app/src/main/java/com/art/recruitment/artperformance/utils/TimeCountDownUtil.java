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
@Deprecated
public class TimeCountDownUtil {

    /**
     * 倒计时监听
     */
    public interface TimeCountListener {

        void onTimeCountDown(long time);

    }

    public static Builder builder(){
        return new Builder();
    }

    /**
     * 释放资源，务必在合适的位置调用该方法
     * @warning 未测试并发计时时该方法是否影响并发计时，比如两个倒计时，只能release掉一个，另一个内存泄漏
     */
    public static void release(){
        Builder.release();
    }

    public static class Builder{

        private static long mTimeSpan;  //时间跨度,即计时的时间范围
        private static long mCountInterval;  //计时时间间隔
        private static TimeUnit mTimeUnit;  //时间间隔单位
        private static TimeCountListener mTimeCountListener;  //计时时间监听。
        private static Disposable mDisposable;


        /**
         * 倒计时时间跨度
         *
         * @param timeSpan
         * @return
         */
        public Builder timeSpan(long timeSpan) {
            mTimeSpan = timeSpan;
            return this;
        }

        /**
         * 两次计时的时间间隔
         *
         * @param interval
         * @return
         */
        public Builder countInterval(long interval) {
            mCountInterval = interval;
            return this;
        }

        /**
         * 计时时间间隔的单位
         *
         * @param timeUnit
         * @return
         */
        public Builder timeUnit(TimeUnit timeUnit) {
            mTimeUnit = timeUnit;
            return this;
        }

        public Builder listener(TimeCountListener listener) {
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
                            if (mTimeCountListener == null){
                                throw new IllegalArgumentException("请设置TimeCountListener");
                            }

                            if (mTimeSpan - aLong <0){
                                release();
                                return;
                            }

                            mTimeCountListener.onTimeCountDown(mTimeSpan-aLong);

                        }
                    });
        }


        private static void release(){
            if (mDisposable != null && !mDisposable.isDisposed()){
                mDisposable.dispose();
            }
        }

    }

}
