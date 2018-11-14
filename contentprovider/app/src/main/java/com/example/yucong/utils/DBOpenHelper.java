package com.example.yucong.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "persons.db", null, 2);//<��>/databases/
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//创建时执行
		db.execSQL("CREATE TABLE person(personid integer primary key autoincrement, name varchar(20), phone VARCHAR(12) )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//更新时执行
		db.execSQL("ALTER TABLE person ADD amount integer");
	}

}