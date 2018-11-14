package com.example.yucong.sharepreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yucong.file.R;

public class KouLing extends AppCompatActivity {
    EditText editText;
    Button btnSet;
    Button btnGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kou_ling);
        editText=findViewById(R.id.txt);
        btnSet=findViewById(R.id.btnSet);
        btnGet=findViewById(R.id.btnGet);
        btnSet.setOnClickListener(new ViewOcl());
        btnGet.setOnClickListener(new ViewOcl());


    }


    class ViewOcl implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btnSet:
                    //步骤1：获取输入值
                    String code = editText.getText().toString().trim();
                    //步骤2-1：创建一个SharedPreferences.Editor接口对象，lock表示要写入的XML文件名，MODE_PRIVATE本应用写操作
                    SharedPreferences.Editor editor = getSharedPreferences("lock",MODE_PRIVATE).edit();
                    //步骤2-2：将获取过来的值放入文件
                    editor.putString("code", code);
                    //步骤3：提交
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "口令设置成功", Toast.LENGTH_LONG).show();
                    break;
                case R.id.btnGet:
                    //步骤1：创建一个SharedPreferences接口对象
                    SharedPreferences read = getSharedPreferences("lock", MODE_PRIVATE);
                    //步骤2：获取文件中的值
                    String value = read.getString("code", "");
                    Toast.makeText(getApplicationContext(), "口令为："+value, Toast.LENGTH_LONG).show();

                    break;

            }
        }

    }



}
