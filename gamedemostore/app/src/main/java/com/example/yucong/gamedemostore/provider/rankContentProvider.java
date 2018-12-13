package com.example.yucong.gamedemostore.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.yucong.gamedemostore.db.DBOpenHelper;

public class rankContentProvider extends ContentProvider {
    private DBOpenHelper dbOpenHelper;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int RANkS = 1;
    private static final int RANK = 2;
    private static final int PATH = 3;
    static{
        MATCHER.addURI("com.example.yucong.provider.rank", "rank", RANkS);
        MATCHER.addURI("com.example.yucong.provider.rank", "rank/#", RANK);
        MATCHER.addURI("com.example.yucong.provider.rank", "path", PATH);
    }
    @Override
    public boolean onCreate() {

        dbOpenHelper = new DBOpenHelper(this.getContext());
        return true;
    }











    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case RANkS:
                long rowid = db.insert("rank", "name", values);//
                //  content://cn.itcast.provides.personprovider/person/10
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);
                this.getContext().getContentResolver().notifyChange(uri, null);// null 不指定具体观察对象 观查数据是否发生改变 改变之后将uri发送到数据中心
                return insertUri;

            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (MATCHER.match(uri)){

            case RANK:

                return  db.query("rank", projection, selection, selectionArgs, null, null, sortOrder);
            case RANkS:

                return db.query("rank", projection, selection, selectionArgs, null, null, sortOrder);
            case PATH:
                 String sql="select  name, score from rank   order by score desc ,time asc";
                return db.rawQuery(sql,null);


            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);


        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int num = 0;
        switch (MATCHER.match(uri)) {
            case RANkS:
                num = db.update("rank", values, selection, selectionArgs);//
                break;
            case RANK:
                long rowid = ContentUris.parseId(uri);//
                String where = "id="+ rowid;  //
                if(selection!=null && !"".equals(selection.trim())){
                    where += " and "+ selection;//
                }
                num = db.update("rank", values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
        return num;
    }
}
