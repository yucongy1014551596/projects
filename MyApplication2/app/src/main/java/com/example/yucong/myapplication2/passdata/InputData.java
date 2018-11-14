package com.example.yucong.myapplication2.passdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yucong.myapplication2.R;

public class InputData extends Activity {
    private EditText editName;
    private EditText editPsw;
    private RadioButton MradioButton;
    private RadioButton FradioButton;
    private  Button button;
    private final static int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editName=findViewById(R.id.username);
        editPsw=findViewById(R.id.password);
        FradioButton=findViewById(R.id.radioFemale);
        MradioButton=findViewById(R.id.radioMale);
        button=findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passDate();
            }
        });
    }

    /**
     * 传递参数
     */
   public  void   passDate(){
       Intent intent=new Intent();
       intent.setClassName("com.example.yucong.myapplication2","com.example.yucong.myapplication2.passdata.ShowData");
       Bundle bundle = new Bundle();
       bundle.putString("username",editName.getText().toString());
       bundle.putString("password",editPsw.getText().toString());
       String str="";
       if(FradioButton.isChecked()){
           str="女";
       }else if(MradioButton.isChecked()){
           str="男";
       }
       bundle.putString("sex",str);
       intent.putExtras(bundle);


       startActivityForResult(intent, REQUEST_CODE);//启动activity  根据两个参数  判断数据来源
//       startActivity(intent);
    }

    /**
     * startActivityForResult（）自动调用函数
     * 处理返回数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)

    {


        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==REQUEST_CODE)
        {

                String str=  data.getStringExtra("data");

                Toast.makeText(InputData.this, str, Toast.LENGTH_LONG).show();

        }
    }
}
