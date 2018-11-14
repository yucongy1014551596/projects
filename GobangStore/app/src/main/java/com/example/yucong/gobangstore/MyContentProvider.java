package com.example.yucong.gobangstore;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static final int USER_DIR = 0;
    private static final int USER_ITEM = 1;
    private static final int CHESS_DIR = 2;
    private static final int CHESS_ITEM = 3;
    private static final int ONLINE_DIR = 4;
    private static final int ONLINE_ITEM = 5;

    public static final String AUTHORITY = "com.example.mrpeng.gobang.provider";
    private static UriMatcher uriMatcher;

    private MySqliteHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"User",USER_DIR);
        uriMatcher.addURI(AUTHORITY,"User/#",USER_ITEM);
        uriMatcher.addURI(AUTHORITY,"Chess",CHESS_DIR);
        uriMatcher.addURI(AUTHORITY,"Chess/#",CHESS_ITEM);
        uriMatcher.addURI(AUTHORITY,"Online",ONLINE_DIR);
        uriMatcher.addURI(AUTHORITY,"Online/#",ONLINE_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MySqliteHelper(getContext(),"GameData.db",null,3);//猜测当第一次运行，创建内容提供器的时候会执行，后面此方法将不再执行。验证结果如此！事实证明，当另一个程序通过内容提供器来访问的时候，此方法会得到执行。
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                cursor = db.query("User",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                cursor = db.query("User",projection,"id=?",new String[] {userId},null,null,sortOrder);
                break;
            case CHESS_DIR:
                cursor = db.query("Chess",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CHESS_ITEM:
                String chessId = uri.getPathSegments().get(1);
                cursor = db.query("Chess",projection,"id=?",new String[] {chessId},null,null,sortOrder);
                break;
            case ONLINE_DIR:
                cursor = db.query("Online",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ONLINE_ITEM:
                String uid = uri.getPathSegments().get(1);
                cursor = db.query("Online",projection,"id=?",new String[] {uid},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
            case USER_ITEM:
                long newUserId = db.insert("User",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/User/" + newUserId);
                break;
            case CHESS_DIR:
            case CHESS_ITEM:
                long newChessId = db.insert("Chess",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/Chess/" + newChessId);
                break;
            case ONLINE_DIR:
            case ONLINE_ITEM:
                long newUid = db.insert("Online",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/Online/" + newUid);
                break;
        }
        return uriReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                updatedRows = db.update("User",values,selection,selectionArgs);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                updatedRows = db.update("User",values,"id=?",new String[] {userId});
                break;
            case CHESS_DIR:
                updatedRows = db.update("Chess",values,selection,selectionArgs);
                break;
            case CHESS_ITEM:
                String chessId = uri.getPathSegments().get(1);
                updatedRows =db.update("Chess",values,"id=?",new String[] {chessId});
                break;
            case ONLINE_DIR:
                updatedRows = db.update("Online",values,selection,selectionArgs);
                break;
            case ONLINE_ITEM:
                String uid = uri.getPathSegments().get(1);
                updatedRows = db.update("Online",values,"id=?",new String[] {uid});
                break;
        }

        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                deletedRows = db.delete("User",selection,selectionArgs);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                deletedRows = db.delete("User","id=?",new String[] {userId});
                break;
            case CHESS_DIR:
                deletedRows = db.delete("Chess",selection,selectionArgs);
                break;
            case CHESS_ITEM:
                String chessId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Chess","id=?",new String[] {chessId});
                break;
            case ONLINE_DIR:
                deletedRows = db.delete("Online",selection,selectionArgs);
                break;
            case ONLINE_ITEM:
                String uid = uri.getPathSegments().get(1);
                deletedRows = db.delete("Online","id=?",new String[] {uid});
                break;
        }

        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){
            case USER_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.mrpeng.gobang.provider.User";
            case USER_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.mrpeng.gobang.provider.User";
            case CHESS_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.mrpeng.gobang.provider.Chess";
            case CHESS_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.mrpengg.gobang.provider.Chess";
            case ONLINE_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.mrpeng.gobang.provider.Online";
            case ONLINE_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.mrpeng.gobang.provider.Online";
        }
        return null;
    }
}
