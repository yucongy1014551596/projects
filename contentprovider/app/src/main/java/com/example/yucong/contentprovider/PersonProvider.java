package com.example.yucong.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.yucong.utils.DBOpenHelper;

public class PersonProvider extends ContentProvider{
	private DBOpenHelper dbOpenHelper;
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS = 1;
	private static final int PERSON = 2;
	static{
		MATCHER.addURI("cn.itcast.providers.personprovider", "person", PERSONS);
		MATCHER.addURI("cn.itcast.providers.personprovider", "person/#", PERSON);
	}
	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBOpenHelper(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);

		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			return db.query("person", projection, where, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

    /**
     *
     * @param uri  如果制定的数据为多条，则以"vnd.android.cursor.dir“ 开头
     *             单条的话  就"vnd.android.cursor.item”
     * @return  获取当前uri指定数据的类型
     */

	@Override
	public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/person";
            case PERSON:
                return "vnd.android.cursor.item/person";
            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			long rowid = db.insert("person", "name", values);//����ֵ
			//  content://cn.itcast.provides.personprovider/person/10
			Uri insertUri = ContentUris.withAppendedId(uri, rowid);
			this.getContext().getContentResolver().notifyChange(uri, null);// null 不指定具体观察对象 观查数据是否发生改变 改变之后将uri发送到数据中心
			return insertUri;

		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int num = 0;
        switch (MATCHER.match(uri)) {
            case PERSONS:
                num = db.delete("person", selection, selectionArgs);
                break;
            case PERSON:
                long rowid = ContentUris.parseId(uri);//获取路径中的数字
                String where = "personid="+ rowid;
                if(selection!=null && !"".equals(selection.trim())){
                    where += " and "+ selection;
                }
                num = db.delete("person", where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
        }
        return num;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch (MATCHER.match(uri)) {
		case PERSONS:
			num = db.update("person", values, selection, selectionArgs);//selection ����selectionArgs  ռλ�� 
			break;
		case PERSON:
			long rowid = ContentUris.parseId(uri);//��ȡpath�������
			String where = "personid="+ rowid;  //��ƴ����
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;//������洫������  �ڼ���������� 
			}
			num = db.update("person", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
		return num;
		
	}

}
