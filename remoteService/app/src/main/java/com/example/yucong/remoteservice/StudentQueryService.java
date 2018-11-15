package com.example.yucong.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class StudentQueryService extends Service {

    private static final String TAG = "StudentQueryService";


    private String[] names = {"张三", "李四", "王五"};


    public StudentQueryBinder binder = new StudentQueryBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: 已经绑定了");
       return binder;
    }
    private String query(int number){
        if(number > 0 && number < 4){
            return names[number - 1];
        }
        return null;
    }

    private final class StudentQueryBinder extends StudentQuery.Stub{


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String queryStudent(int number) throws RemoteException {
            Log.i("lll", "queryStudent: "+number);
            return query(number);
        }
    }
}
