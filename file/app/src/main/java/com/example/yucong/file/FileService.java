package com.example.yucong.file;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.os.ParcelFileDescriptor.MODE_APPEND;


public class FileService extends AppCompatActivity{
    EditText editText;
    Button btnSave;
    Button btnRead;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        editText=findViewById(R.id.txt);
        btnSave=findViewById(R.id.save);
        btnRead=findViewById(R.id.read);
       String inPutText=load();

        if (!TextUtils.isEmpty(inPutText)) {
            editText.setText(inPutText);
            editText.setSelection(inPutText.length());
            Toast.makeText(getApplicationContext(), "Restory success ", Toast.LENGTH_LONG).show();

        }


        btnSave.setOnClickListener(new ViewOcl());
        btnRead.setOnClickListener(new ViewOcl());

    }



    class ViewOcl implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.save:
                    //步骤1：获取输入值
                    String code = editText.getText().toString().trim();
                    save(code);
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                    break;
                case R.id.read:
                    String value = load();

                    Toast.makeText(getApplicationContext(), "存入数据为："+value, Toast.LENGTH_LONG).show();

                    break;

            }
        }

    }







    public String read() {
        try {
            FileInputStream inStream = this.openFileInput("message.txt");
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            while ((hasRead = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, hasRead));
            }

            inStream.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String msg){
        // 步骤1：获取输入值
        if(msg == null) return;
        try {
            // 步骤2:创建一个FileOutputStream对象,MODE_APPEND追加模式
            FileOutputStream fos = openFileOutput("message.txt",
                    MODE_APPEND);
            // 步骤3：将获取过来的值放入文件
            fos.write(msg.getBytes());
            // 步骤4：关闭数据流
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 字符流存数据
     * @param msg
     */

    public void save(String msg){
        FileOutputStream fos =null;
        BufferedWriter writer=null;
        // 步骤1：获取输入值
        if(msg == null) return;
        try {
            // 步骤2:创建一个FileOutputStream对象,MODE_APPEND追加模式
             fos = openFileOutput("message.txt",MODE_PRIVATE);
             writer=new BufferedWriter(new OutputStreamWriter(fos));
             writer.write(msg);

            // 步骤4：关闭数据流
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取字符流
     * @return
     */

    public String load() {
        FileInputStream inStream =null;
        BufferedReader bufferedReader=null;
        StringBuilder content = new StringBuilder();

        try {
             inStream = this.openFileInput("message.txt");
             bufferedReader=new BufferedReader(new InputStreamReader(inStream));
             String line="";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }




}
