package com.example.administrator.myhookdemo.HookDemo;

import android.os.Bundle;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Hook应用破解后--QQ授权登入问题
 */
public class Hookqq implements IXposedHookLoadPackage {

    /**
     * 贪吃蛇大作战MD5签名
     */
    private String qm = "678a930b9829b54a44f92a840916f7d1";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        //将包名不是 com.tencent.mobileqq 的应用剔除掉
        if (loadPackageParam.packageName.equals("com.tencent.mobileqq")) {

            XposedBridge.log("开始Hook了");

            XposedHelpers.findAndHookMethod("com.tencent.open.agent.AgentActivity",
                    loadPackageParam.classLoader,
                    "doOnCreate",
                    Bundle.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            XposedBridge.log("你Hook到授权方法了！！！");

                            XposedHelpers.findAndHookMethod("java.lang.String",
                                    loadPackageParam.classLoader,
                                    "toLowerCase",
                                    new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);

                                            XposedBridge.log("你Hook到授权方法中的String方法了！！！");
                                            param.setResult(qm);
                                        }
                                    });
                        }

                    }
            );

            //XC_MethodReplacement继承自XC_MethodHook，方法里面的内容不执行
//            XposedHelpers.findAndHookMethod("com.tencent.open.agent.AgentActivity",
//                    loadPackageParam.classLoader,
//                    "doOnCreate",
//                    Bundle.class, new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
//                            return null;
//                        }
//                    });

        }
    }
}
