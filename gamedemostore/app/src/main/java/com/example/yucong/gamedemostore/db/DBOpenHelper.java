package com.example.yucong.gamedemostore.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {

		super(context, "gameStores.db", null, 2);///databases/
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//创建时执行
		db.execSQL("CREATE TABLE user(userid integer primary key autoincrement, account varchar(50), password VARCHAR(25) )");
		db.execSQL("CREATE TABLE score(id integer primary key autoincrement, userid integer,score varchar(40), highScore VARCHAR(50) )");
		db.execSQL("CREATE TABLE rank(id integer primary key autoincrement, imageid integer,name varchar(40), score integer,time long )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//更新时执行
		db.execSQL("drop table if exists user");
		db.execSQL("drop table if exists score");
		db.execSQL("drop table if exists rank");
		onCreate(db);

	}

}