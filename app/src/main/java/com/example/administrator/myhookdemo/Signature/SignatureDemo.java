package com.example.administrator.myhookdemo.Signature;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.example.administrator.myhookdemo.IgetSign;

public class SignatureDemo {

    private String pkgName = "";
    private Context context;
    private IgetSign igetSign;

    public SignatureDemo(Context context, String pkgName, IgetSign getSign){
        this.context = context;
        this.pkgName = pkgName;
        this.igetSign = getSign;
    }

    public void getSignature(){

        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES).signatures;
            Log.d("zzzzz", "sign>>>>>>>1: " + signatureArr[0].hashCode());
            Log.d("zzzzz", "sign>>>>>>>2: " + signatureArr[0].toCharsString());

            //MD5加密
//            MessageDigest instance = MessageDigest.getInstance("MD5");
//            instance.update(signatureArr[0].toByteArray());

            igetSign.signBack(signatureArr[0].toCharsString());
        } catch (Exception e) {
            e.printStackTrace();
            igetSign.signBack("未找到该应用，请检查该应用是否已安装或者包名是否有误！！！");
        }


    }
}
