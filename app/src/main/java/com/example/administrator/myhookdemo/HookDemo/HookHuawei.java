package com.example.administrator.myhookdemo.HookDemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookHuawei implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.shmiren.ftyxxxhd.huawei")){
            XposedBridge.log("开始Hook了");

            XposedHelpers.findAndHookMethod("com.shmiren.ftyxxxhd.huawei.UnityPlayerActivity",
                    loadPackageParam.classLoader,
                    "a",
                    Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("你Hook到授权方法了！！！");
                            XposedBridge.log(">>>>>2" + param.getResult());
//                            param.setResult("AAC1C21547D68F0A1671233B1CC189CA8924111D3E9EEDB12C2207D0A688F580");
                        }
                    });
        }

    }
}
