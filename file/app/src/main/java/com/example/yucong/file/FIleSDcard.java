package com.example.yucong.file;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class FIleSDcard extends AppCompatActivity {
    EditText editText;
    Button btnSave;
    Button btnRead;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sdcard);
        editText=findViewById(R.id.txt);
        btnSave=findViewById(R.id.save);
        btnRead=findViewById(R.id.read);
        btnSave.setOnClickListener(new ViewOcl());
        btnRead.setOnClickListener(new ViewOcl());
        verifyStoragePermissions(this);
    }



    class ViewOcl implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.save:
                    //步骤1：获取输入值
                    String code = editText.getText().toString().trim();
                    write(code);
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                    break;
                case R.id.read:
                    String value = read();

                    Toast.makeText(getApplicationContext(), "存入数据为："+value, Toast.LENGTH_LONG).show();

                    break;

            }
        }

    }



    // 文件写操作函数
    private void write(String content) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在

            File SdPath=Environment.getExternalStorageDirectory();

            File file = new File(SdPath,"data.txt"); // 定义File类对象
            Log.i("content",content);

            FileOutputStream out=null;

//            PrintStream out = null; // 打印流对象用于输出
            try {
               out= new FileOutputStream(file); // 追加文件

               out.write(content.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (out != null) {
                    try {
                        out.close(); // 关闭打印流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else { // SDCard不存在，使用Toast提示用户
            Toast.makeText(this, "保存失败，SD卡不存在！", Toast.LENGTH_LONG).show();
        }
    }

    // 文件读操作函数
    private String read() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在
            File SdPath=Environment.getExternalStorageDirectory();

            File file = new File(SdPath,"data.txt"); // 定义File类对象


            FileInputStream fis=null;
            try {
                fis= new FileInputStream(file); // 实例化Scanner
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fis));
                String data= bufferedReader.readLine();
                Log.i("data",data);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close(); // 关闭打印流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else { // SDCard不存在，使用Toast提示用户
            Toast.makeText(this, "读取失败，SD卡不存在！", Toast.LENGTH_LONG).show();
        }
        return null;
    }
//    //动态获取内存存储权限
//    public static void verifyStoragePermissions(Activity activity) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE);
//        }
//    }


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
