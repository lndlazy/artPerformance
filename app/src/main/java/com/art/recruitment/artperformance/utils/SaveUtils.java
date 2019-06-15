//package com.art.recruitment.artperformance.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import java.util.Set;
//
///**
// * Created by linaidao on 2018/10/16.
// */
//
//public class SaveUtils {
//
//    //城市名称
//    public static final String CITY_NAME = "CITY_NAME";
//    //城市编码
//    public static final String CITY_CODE = "CITY_CODE";
//
//
//
//    public static String CONFIG = "art_performance";//sp配置文件名称
//
////    private static SaveUtils instance;//单例模式 双重检查锁定
////
////    public static SaveUtils getInstance() {
////        if (instance == null) {
////            synchronized (SaveUtils.class) {
////                if (instance == null) {
////                    instance = new SaveUtils();
////                }
////            }
////        }
////        return instance;
////    }
//
//
//    /**
//     * 保存数据的方法，将不同类型的数据保存到文件中
//     *
//     * @param context
//     * @param key
//     * @param object
//     */
//    public static void put(Context context, String key, Object object) {
//
//        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        if (object instanceof String) {
//            editor.putString(key, (String) object);
//        } else if (object instanceof Integer) {
//            editor.putInt(key, (Integer) object);
//        } else if (object instanceof Boolean) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if (object instanceof Float) {
//            editor.putFloat(key, (Float) object);
//        } else if (object instanceof Long) {
//            editor.putLong(key, (Long) object);
//        } else {
//            editor.putString(key, object.toString());
//        }
//
//        editor.apply();
//    }
//
//    /**
//     * 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
//     *
//     * @param context
//     * @param key
//     * @param defaultObject
//     * @return
//     */
//    public static Object get(Context context, String key, Object defaultObject) {
//        SharedPreferences sp = context.getSharedPreferences(CONFIG,
//                Context.MODE_PRIVATE);
//
//        if (defaultObject instanceof String) {
//            return sp.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer) {
//            return sp.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean) {
//            return sp.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float) {
//            return sp.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long) {
//            return sp.getLong(key, (Long) defaultObject);
//        }
//
//        return null;
//    }
//
//
////    public void putInt(String key, int value) {
////        Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
////    }
////
////    /**
////     * 获取缓存中的int值，默认-1
////     *
////     * @param key
////     * @return
////     */
////    public int getInt(String key) {
////        return Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).getInt(key, -1);
////    }
////
////
////    public void putString(String key, String value) {
////        Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).edit().putString(key, value).apply();
////    }
////
////    public void putLong(String key, long value) {
////        Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).edit().putLong(key, value).apply();
////    }
////
////    public String getString(String key) {
////        return Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).getString(key, "");
////    }
////
////    public String getStringWithDefault(String key, String defaultValue) {
////        return Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).getString(key, defaultValue);
////    }
////
////    /**
////     * 获取缓存中的long值，默认0
////     *
////     * @param key
////     * @return
////     */
////    public long getLong(String key) {
////        return Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).getLong(key, 0);
////    }
////
////    public void putBoolean(String key, boolean value) {
////        Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
////    }
//
////    /**
////     * 获取缓存中的boolean值
////     *
////     * @param key
////     * @return
////     */
////    public boolean getBoolean(String key, boolean defaultValue) {
////        return Mapplication.getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
////    }
//
//
//    public static void putSet(Context context, String key, Set<String> value) {
//
//        SharedPreferences sp = context.getSharedPreferences(CONFIG,
//                Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor edit = sp.edit();
//
//        edit.putStringSet(key, value).apply();
//    }
//
//
//    public static Set<String> getSet(Context context, String key, Set<String> defalut) {
//
//        SharedPreferences sp = context.getSharedPreferences(CONFIG,
//                Context.MODE_PRIVATE);
//
//        return sp.getStringSet(key, defalut);
//    }
//
//}
