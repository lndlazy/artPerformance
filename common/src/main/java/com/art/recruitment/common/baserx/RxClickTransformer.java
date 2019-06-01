package com.art.recruitment.common.baserx;

import com.art.recruitment.common.base.config.BaseConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 *  @Desc :RxBinding点击事件的transformer
 */

public class RxClickTransformer {

    /**
     * @return
     */
    public static ObservableTransformer<Object,Object> getClickTransformer() {

        return new ObservableTransformer<Object,Object>() {

            @Override
            public ObservableSource<Object> apply(Observable<Object> upstream) {
                return upstream.
                        throttleFirst(BaseConfig.BUTTON_CLICK_INTERVAL, TimeUnit.MILLISECONDS).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
