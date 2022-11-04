package com.example.test;

//原来打算用application类存 发现调用函数闪退 就没用了
//没用！！！！！！！！！！！！！
        import android.annotation.TargetApi;
        import android.app.Application;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Build;

/**
 * Created by outlier on 2017/7/20.
 */

public class BaseApplication  extends Application {
    private static String PREF_NAME = "yixing_app.pref";


    static Context _context;
    private static boolean sIsAtLeastGB;

    private String name;
    private String gender;
    private String age;
    private  int q1;
    private  int q2;
    private  int q3;
    private  int q4;
    private  int q5;
    private  int q6;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }

    }

    @Override
    public void onCreate() {

        name = "默认姓名";
        gender = "默认性别";
        age = "默认年龄";
        super.onCreate();
        _context = getApplicationContext();
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }



    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }



    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

}