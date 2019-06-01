package com.art.recruitment.common.view;

import com.art.recruitment.common.base.BaseBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MiaResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    TypeAdapter<T> adapter;

    public MiaResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        try {
            String body = value.string();
            BaseBean baseBean = gson.fromJson(body,BaseBean.class);
            if (baseBean.getCode() == 200 && baseBean.getData() == null) {

                String j = "{\"code\":200,\"data\":{}}";
                return adapter.fromJson(j);
            }
//            LogUtils.e(body);
            return adapter.fromJson(body);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}