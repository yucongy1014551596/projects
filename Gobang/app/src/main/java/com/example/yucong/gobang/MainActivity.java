package com.example.yucong.gobang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences pre;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //region 隐藏标题
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //endregion

        //region 设置为用户所选语言，需在加载界面之前配置好
        pre = getSharedPreferences("language_config",MODE_PRIVATE);
        String str = pre.getString("language","zh");
        if(str.equals("zh"))BothSide.language=1;
        else BothSide.language =0;
        setConf(str);
        //endregion

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 获取控件
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        Button switchButton =(Button) findViewById(R.id.switch_language);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        switchButton.setOnClickListener(this);
        //endregion

        //region 恢复对局数据
        SharedPreferences preSave  = getSharedPreferences("SaveState",MODE_PRIVATE);
        boolean isSaved = preSave.getBoolean("isSaved",false);
        if(isSaved){
            BothSide.isSaved = isSaved;
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }else{
            BothSide.isSaved = false;
        }
        //endregion
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login: {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.putExtra("which",1);
                startActivity(intent);
            }
            break;
            case R.id.register: {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.putExtra("which",0);
                startActivity(intent);
            }
            break;
            case R.id.switch_language:{
                swtichLanguage();
            }
            break;
        }
    }

    //切换语言
    private void swtichLanguage(){
        editor = pre.edit();
        String  str = Locale.getDefault().getLanguage();
        if(str.equals("zh")) str = "en";
        else str = "zh";
        editor.putString("language",str);//保留语言配置
        editor.apply();
        setConf(str);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    //保存语言设置，并更新
    private void setConf(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
    }
}
