package com.example.yucong.remoteserviceclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yucong.remoteservice.StudentQuery;



public class MainActivity extends AppCompatActivity {

    private EditText numberText;
    private TextView resultView;
    private StudentQuery studentQuery;


    private ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {

            studentQuery = StudentQuery.Stub.asInterface(service);

        }

        public void onServiceDisconnected(ComponentName name) {
            studentQuery = null;
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numberText = findViewById(R.id.number1);
        resultView = findViewById(R.id.resultView);
        Intent service = new Intent();
        service.setComponent(new ComponentName("com.example.yucong.remoteservice", "com.example.yucong.remoteservice.StudentQueryService"));
        //service.setAction("cn.itcast.student.query");
        //service.setPackage(this.getPackageName());
        boolean isSuc = bindService(service, conn, BIND_AUTO_CREATE);
        Log.e("MainActivity", "bindSuc:"+isSuc);
    }

    public void queryStudent1(View v) {
        String number2 = numberText.getText().toString();
        int number = Integer.valueOf(number2);


        try {

         resultView.setText(studentQuery.queryStudent(number));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }


}
