package com.example.yucong.gobangstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by think on 2018/10/2.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement,"
            + "account text,"
            + "password text,"
            + "win integer,"
            + "lose integer,"
            + "grade integer)";

    public static final String CREATE_CHESS = "create table Chess ("
            + "id integer primary key autoincrement,"
            + "blackId integer,"
            + "whiteId integer,"
            + "winId integer)";

    public static final String CREATE_ONLINE = "create table Online ("
            + "id integer primary key,"
            + "account text,"
            + "isPlaying integer)";

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CHESS);
        db.execSQL(CREATE_ONLINE);
        db.execSQL("insert into online values(1,'zhou',0)");
        db.execSQL("insert into online values(2,'wang',0)");
        db.execSQL("insert into online values(3,'hu',0)");
        db.execSQL("insert into online values(4,'liu',1)");
        db.execSQL("insert into online values(5,'sun',0)");
        db.execSQL("insert into online values(6,'li',0)");

        db.execSQL("insert into user values(1,'zhou','123',0,0,0)");
        db.execSQL("insert into user values(2,'wang','123',0,0,0)");
        db.execSQL("insert into user values(3,'hu','123',0,0,0)");
        db.execSQL("insert into user values(4,'liu','123',0,0,0)");
        db.execSQL("insert into user values(5,'sun','123',0,0,0)");
        db.execSQL("insert into user values(6,'li','123',0,0,0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Chess");
        db.execSQL("drop table if exists Online");
        onCreate(db);
    }
}
