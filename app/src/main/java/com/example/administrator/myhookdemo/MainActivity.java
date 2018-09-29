package com.example.administrator.myhookdemo;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myhookdemo.Signature.SignatureDemo;


public class MainActivity extends AppCompatActivity {

    private Button wifiBtn;
    private Button signatureBtn;
    private TextView signTx;
    private EditText bgName;
    private String pkgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signTx = findViewById(R.id.textView_signature);
        bgName = findViewById(R.id.bg_name);
        wifiBtn = findViewById(R.id.button_wifi);
        signatureBtn = findViewById(R.id.get_signature);

        MyToast("你好！！！我的朋友");

        wifiInit();
        getSign();

    }

    private void MyToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置WIFI配置
     */
    private void wifiInit(){

        wifiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }

    /**
     * 获取APP签名
     */
    private void getSign(){

        signatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pkgName = bgName.getText().toString();
                Log.d("ZZZZZ", pkgName);
                if (pkgName.equals("")){
                    MyToast("请输入包名");
                }else {
                    SignatureDemo demo = new SignatureDemo(MainActivity.this, pkgName, new getSS());
                    demo.getSignature();
                }
            }
        });

    }

    public class getSS implements IgetSign{

        @Override
        public void signBack(String signature) {
            signTx.setText(signature);
        }
    }
}
