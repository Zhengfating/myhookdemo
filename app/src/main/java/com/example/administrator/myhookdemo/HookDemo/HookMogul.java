package com.example.administrator.myhookdemo.HookDemo;

import android.app.Application;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Hook360加固之魔窟应用--内购破解
 */
public class HookMogul implements IXposedHookLoadPackage {

    private Class clazz;

    private static final String ApkName = "com.clovare.MogulAndroid";


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        if (ApkName.equals(loadPackageParam.packageName)) {

            XposedBridge.log("开始Hook了！！！");

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    ClassLoader loader = ((Context) param.args[0]).getClassLoader();

                    try {
                        clazz = loader.loadClass("com.clovare.morgul2.PayResult");
                    } catch (Exception e) {
                        XposedBridge.log("Hook的方法没进去");
                    }

                    if (clazz != null) {
                        XposedHelpers.findAndHookMethod(clazz, "getResultStatus", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);

                                XposedBridge.log("dimond: " + param.getResult());
                                param.setResult("9000");
                                XposedBridge.log("恭喜你Hook成功了");
                            }
                        });
                    }
                }
            });
        }
    }
}
