package com.example.yucong.gamedemostore.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.yucong.gamedemostore.db.DBOpenHelper;
import com.example.yucong.gamedemostore.entity.user;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class UserContentProvider  extends ContentProvider {

    private DBOpenHelper dbOpenHelper;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USERS = 1;
    private static final int USER = 2;
    static{
        MATCHER.addURI("com.example.yucong.gamedemostore.provider.UserContentProvider", "user", USERS);
        MATCHER.addURI("com.example.yucong.gamedemostore.provider.UserContentProvider", "user/#", USER);
    }


    @Override
    public boolean onCreate() {

        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }






    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (MATCHER.match(uri)){

            case USERS:
                String where="";
                if(selection!=null && !"".equals(selection.trim())){
                    where =  selection;//
                }
                return  db.query("user", projection, where, selectionArgs, null, null, sortOrder);



            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);


        }







    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case USERS:
                long rowid = db.insert("user", "account", values);//
                //  content://cn.itcast.provides.personprovider/person/10
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);
                this.getContext().getContentResolver().notifyChange(uri, null);// null 不指定具体观察对象 观查数据是否发生改变 改变之后将uri发送到数据中心
                return insertUri;

            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }

    }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int num = 0;
        switch (MATCHER.match(uri)) {
            case USERS:
                num = db.update("user", values, selection, selectionArgs);//
                break;
            case USER:
                long rowid = ContentUris.parseId(uri);//
                String where = "userid="+ rowid;  //
                if(selection!=null && !"".equals(selection.trim())){
                    where += " and "+ selection;//
                }
                num = db.update("user", values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
        return num;
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int num = 0;
        switch (MATCHER.match(uri)) {
            case USERS:
                num = db.delete("user", selection, selectionArgs);
                break;
            case USER:
                long rowid = ContentUris.parseId(uri);//获取路径中的数字
                String where = "userid="+ rowid;
                if(selection!=null && !"".equals(selection.trim())){
                    where += " and "+ selection;
                }
                num = db.delete("user", where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
        return num;
    }
}
