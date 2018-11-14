package com.example.yucong.litepal;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        Button addDatabase = (Button) findViewById(R.id.add_data);
        Button updateDatabase = (Button) findViewById(R.id.update_data);
        Button delteDatabase = (Button) findViewById(R.id.delete_data);
        Button queryDatabase = (Button) findViewById(R.id.query_data);
        Button querysql = (Button) findViewById(R.id.query_sql);
        createDatabase.setOnClickListener(new button());
        addDatabase.setOnClickListener(new button());
        updateDatabase.setOnClickListener(new button());
        queryDatabase.setOnClickListener(new button());
        delteDatabase.setOnClickListener(new button());
        querysql.setOnClickListener(new button());

    }



    private class button  implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.create_database:
                    LitePal.getDatabase();
                    Log.i(TAG, "onClick: ");
//                    Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG);
                    break;

                case R.id.add_data:
                    Book book=new Book();
                    book.setName("yucong");
                    book.setAuthor("dan");
                    book.setPages(5);
                    book.setPress("unkown");
                    book.save();
                    Log.i(TAG, "添加成功 ");break;

                case R.id.update_data:
                    Book book1=new Book();
                    book1.setName("yangyang");

                    book1.setPages(20);
                    book1.setPrice(50);

                   book1.updateAll("author=?","dan");
                    Log.i(TAG, "修改成功 ");break;
                case R.id.query_data:
                    List<Book> books= DataSupport.findAll(Book.class);

                    for (Book book3 :books){
                        Log.d("MainActivity", "book id is " + book3.getId());
                        Log.d("MainActivity", "book name is " + book3.getName());
                        Log.d("MainActivity", "book author is " + book3.getAuthor());
                        Log.d("MainActivity", "book pages is " + book3.getPages());
                        Log.d("MainActivity", "book price is " + book3.getPrice());
                        Log.d("MainActivity", "book press is " + book3.getPress());
                    }
                  break;
                case R.id.query_sql:
                    Cursor books1= DataSupport.findBySQL("select * from book");
                    if(books1!=null){
                        while (books1.moveToNext()){
                            Log.d("MainActivity", "book name is " + books1.getString(books1.getColumnIndex("name")));
                        }

                    }

                    break;
                case R.id.delete_data:
                    DataSupport.delete(Book.class,2);

                    Log.i(TAG, "删除成功 ");break;


            }
        }
    }
}
