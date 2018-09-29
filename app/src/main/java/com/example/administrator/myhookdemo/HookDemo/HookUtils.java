package com.example.administrator.myhookdemo.HookDemo;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;

/**
 * 获取某个类中的成员变量
 */
public class HookUtils {

    public static <T> T getHookView(XC_MethodHook.MethodHookParam param, String name) throws NoSuchFieldException, IllegalAccessException {

        Class clazz = param.thisObject.getClass();

        // 通过反射获取控件，无论parivate或者public
        Field field = clazz.getDeclaredField(name);

        field.setAccessible(true);

        return  (T) field.get(param.thisObject);
    }
}
