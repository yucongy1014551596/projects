package com.example.yucong.broadcastreceiver.ipdail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yucong.broadcastreceiver.R;

public class MainIp extends AppCompatActivity {
    private EditText editText;
    private SharedPreferences sp;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION"
            ,"android.permission.PROCESS_OUTGOING_CALLS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ip);
        editText=findViewById(R.id.et_number);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        editText.setText(sp.getString("ipnumber",""));
        verifyPermissions(this);

    }



   public  void   click(View view){
      String inputNumber= editText.getText().toString().trim();
         SharedPreferences.Editor editor= sp.edit();
            editor.putString("ipnumber",inputNumber);
            editor.commit();
       Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();

    }
    public static void verifyPermissions(Activity activity) {
        int permission3= ActivityCompat.checkSelfPermission(activity,
                "android.permission.PROCESS_OUTGOING_CALLS");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(permission3!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                    return;
                };
            }else {
                //API 版本在23以下 不需要获取权限
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
