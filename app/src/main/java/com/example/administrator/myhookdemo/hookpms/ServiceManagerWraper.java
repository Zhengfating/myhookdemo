package com.example.administrator.myhookdemo.hookpms;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceManagerWraper {
	
    public static void hookPMS(Context context, String signed, String appPkgName, int hashCode){
        try{
            // 获取全局的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod =
            		activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 获取ActivityThread里面原始的sPackageManager
            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(currentActivityThread);

            // 准备好代理对象, 用来替换原始的对象
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(
                    iPackageManagerInterface.getClassLoader(),
                    new Class<?>[] { iPackageManagerInterface },
                    new PmsHookBinderInvocationHandler(sPackageManager, signed, appPkgName, 0));

            // 1. 替换掉ActivityThread里面的 sPackageManager 字段
            sPackageManagerField.set(currentActivityThread, proxy);

            // 2. 替换 ApplicationPackageManager里面的 mPM对象
            PackageManager pm = context.getPackageManager();
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm, proxy);

        }catch (Exception e){
            Log.d("zzzzz", "hook pms error:"+ Log.getStackTraceString(e));
        }
    }
    
    public static void hookPMS(Context context){
    	String snakeSign = "308202dd308201c5a003020102020435cff6fe300d06092a864886f70d01010b0500301e310e300c06035504071305777568616e310c300a0603550403130367756f3020170d3136303632333032313831375a180f32303736303630383032313831375a301e310e300c06035504071305777568616e310c300a0603550403130367756f30820122300d06092a864886f70d01010105000382010f003082010a0282010100e10d2f30959e8ff6d25bf1a27b10292f3abaf55dcdcb8e568b34b5db280346a60fee0fb1c63d72e596945cec938c24eab99e26215a31220863f880c66319ebf82879c34c546507da7bb5b1f3d8321f60be37fdbe0e4ec0e0a53c375cd5d48f4770a3a1a6e7cd9bccf4623d6afdc536e5523ba5c1932321f5d891556f5bfc38c4195b5ca191e3b3f15a8e1eb3c5c6cffdd18c7d1e4746622d9f620ec38896ed3c57a344c4805b9cd704ae687a025d6579b90a6c2fa692ce3f63782d7ef57b9b4befcc94bac4babe2f917d3fd3102f279e17db9e369ea4031fd700aa46170f3a064a03e893416beca7d99508bc3e150b430e791b50bba203613d79fbf754c362030203010001a321301f301d0603551d0e041604148aa2caac65d4414936d446bb826d7111db757334300d06092a864886f70d01010b05000382010100d3800c43988d1bd6b8c7ec3e58af4bf4e0de022fd6dfbf229e5306dd734b0b646dcf2daca2beba090d29afd169a2a574f4fd2b9b023937f7d7624f10e5949c9816f4154d9edafed95de1a2e30578ae22f9880415e72619de340428705a4113b47c79c57947d273a9c812866f8160a23d40db72257bbb32b379b5ea3fe93256c1e9d4d7f121c5d9f11d64a62c8183f2d2d75df1ec1ddb06c319894a7ecef2c2e007f61bb669b8e9bf77b2a8db934bb06dd1b84afdb328c5e8a25376896880dbf81d7e202ea83db9fbff1342a1e376dffdb59b74267cdde76858c66cd2591db637cb1d9eaa91e1ad68fa25766ceb5ffbdda577ce973cda30f502b38c263c8f8660";
    	hookPMS(context, snakeSign, "com.tencent.mobileqq", 0);
    }
    
}
