package com.example.yucong.file;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

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
}
