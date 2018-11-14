package com.example.yucong.gobangstore;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;//我认为这个纯粹是为了升级之用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在这里反而不能使用getContext()
        dbHelper = new MySqliteHelper(MainActivity.this,"GameData.db",null,3);
        //还要加上下面的语句才能完成数据库的升级，这也就表明，没有数据，将调用oncreate方法，有数据库，且版本号大于老版本，将执行onupgrade方法。
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }
}
