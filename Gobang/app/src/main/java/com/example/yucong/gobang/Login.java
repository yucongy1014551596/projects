package com.example.yucong.gobang;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private Button login;
    private Button register;
    
    private User userI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region 获取控件
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        accountEdit = (EditText) findViewById(R.id.account_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        //endregion

        //region 判断是登录还是注册
        Intent intent = getIntent();
        int which = intent.getIntExtra("which",1);
        if(which == 0){
            login.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
            rememberPass.setVisibility(View.GONE);
            register();
        }else{
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
            rememberPass.setVisibility(View.VISIBLE);
            login();
        }
        //endregion

        //region 恢复对局数据
        if(BothSide.isSaved) {
            SharedPreferences pre = getSharedPreferences("SaveState", MODE_PRIVATE);
            String account = pre.getString("account","");
            String password = pre.getString("password","");
            BothSide.password = password;
            int id = pre.getInt("mid",0);
            if(isValid(account,password)){
                setOnline(account,id);
                Intent intent1 = new Intent(this,Hall.class);
                startActivity(intent1);
                this.finish();
            }
        }
        //endregion
    }

    //登录
    private void login(){
        pref = getSharedPreferences("data",MODE_PRIVATE);

        boolean isRemembered = pref.getBoolean("remember_password",false);
        if(isRemembered){
            accountEdit.setText(pref.getString("account",""));
            passwordEdit.setText(pref.getString("password",""));
            rememberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(isValid(account,password)) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    //跳转至大厅服务
                    Intent intent = new Intent(Login.this,Hall.class);
                    startActivity(intent);
                    Login.this.finish();
                }else{
                    //密码账户错误
                    Toast.makeText(Login.this,"账号或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //注册
    private void register(){
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(account.equals("")||password.equals("")){
                    Toast.makeText(Login.this,"账号或密码不能为空！",Toast.LENGTH_SHORT);
                }else{
                    Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User");
                    ContentValues values = new ContentValues();
                    values.put("account",account);
                    values.put("password",password);
                    values.put("win",0);
                    values.put("lose",0);
                    values.put("grade",0);
                    Uri uriReturn = getContentResolver().insert(uri,values);
                    int id = Integer.parseInt(uriReturn.getPathSegments().get(1));

                    userI = new User();
                    userI.setId(id);
                    userI.setAccount(account);
                    userI.setWin(0);
                    userI.setLose(0);
                    userI.setGrade(0);
                    BothSide.password = password;

                    setOnline(account,id);

                    Intent intent = new Intent(Login.this,Hall.class);
                    startActivity(intent);
                    Login.this.finish();
                }
            }
        });
    }

    //判断登录账户和密码是否正确
    private boolean isValid(String account, String password){
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User");
        Cursor cursor = getContentResolver().query(uri,new String[]{"id","win","lose","grade"},"account=? and password=?",new String[]{account,password},null);
        if(cursor.getCount()==0){
            cursor.close();
            return false;
        }else{
            cursor.moveToFirst();//转到开头
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int win = cursor.getInt(cursor.getColumnIndex("win"));
            int lose = cursor.getInt(cursor.getColumnIndex("lose"));
            int grade = cursor.getInt(cursor.getColumnIndex("grade"));
            cursor.close();
            userI = new User(id,account,win,lose,grade);
            BothSide.password = password;
            setOnline(account,id);
            return true;
        }
    }

    //在登录或注册成功后在数据库将用户设置为在线
    private void setOnline(String account,int id){
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online");
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("account",account);
        values.put("isPlaying",0);
        getContentResolver().insert(uri,values);
        BothSide.Me = userI;
    }
}
