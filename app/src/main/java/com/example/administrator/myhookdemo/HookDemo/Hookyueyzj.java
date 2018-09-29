package com.example.administrator.myhookdemo.HookDemo;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 月圆之夜破解未实现
 */
public class Hookyueyzj implements IXposedHookLoadPackage{

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.ztgame.yyzy")){
            XposedBridge.log("开始Hook了");

            final Class<?> platform = loadPackageParam.classLoader.loadClass("com.ztgame.websdk.payment.common.PayCallbackInfo");

            XposedHelpers.findAndHookMethod("com.ztgame.websdk.payment.common.SDKLibPlatform",
                    loadPackageParam.classLoader,
                    "callback",
                    int.class,
                    platform ,new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("你Hook到授权方法了！！！");
                            XposedBridge.log(">>>>>" + param.args[0]);

                            Method m = platform.getDeclaredMethod("getOrder3rd");
                            m.setAccessible(true);
                            String re = (String) m.invoke(param.args[1]);

                            Method m2 = platform.getDeclaredMethod("getExceptionMsg");
                            m.setAccessible(true);
                            String re2 = (String) m2.invoke(param.args[1]);
                            XposedBridge.log(">>>>>" + re +  "----" + re2);

                            Method m3 = platform.getDeclaredMethod("setExceptionMsg", String.class);
                            m.setAccessible(true);
                            m3.invoke(param.args[1], "支付成功");

                            param.args[0] = 10;

//                            param.setResult("AAC1C21547D68F0A1671233B1CC189CA8924111D3E9EEDB12C2207D0A688F580");
                        }
                    });
        }
    }
}
