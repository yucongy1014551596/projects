package com.example.yucong.contentprovidertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    Context appContext =null;
    @Test
    public void useAppContext() {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.yucong.contentprovidertest", appContext.getPackageName());
    }



    @Test
    public void testInsert() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person");
        ContentResolver resolver = appContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "yucongcong");
        values.put("phone", "1860103838383");
        values.put("amount", "500000000111");
        resolver.insert(uri, values);
    }

    @Test
    public void testDelete() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person/1");
        ContentResolver resolver = appContext.getContentResolver();
        resolver.delete(uri, null, null);
    }


    @Test
    public void testUpdate() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person/1");
        ContentResolver resolver = appContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "zhangxiaoxiao");
        resolver.update(uri, values, null, null);
    }
    @Test
    public void testQuery() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person");
        ContentResolver resolver = appContext.getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, "personid asc");
        String name ="";
        while(cursor.moveToNext()){
             name = cursor.getString(cursor.getColumnIndex("name"));

        }

        Log.i(TAG, name);

        cursor.close();
    }

}
