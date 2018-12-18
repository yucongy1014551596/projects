package com.example.yucong.tetris.chrislee.tetris.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.ActivityMain;

/**
 * Implementation of App Widget functionality.
 */
public class DesktopAppWidget extends AppWidgetProvider {

    // 更新 widget 的广播对应的action
    private final static String ACTION_UPDATE_ALL = "com.ycc.widget.UPDATE_ALL";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.widget);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.desktop_app_widget);
        views.setTextViewText(R.id.widget_txt, widgetText);


        // 设置点击按钮对应的PendingIntent：即点击按钮时，发送广播。
        views.setOnClickPendingIntent(R.id.widget_sendBrodcast, getBrodcastPendingIntent(context));
        views.setOnClickPendingIntent(R.id.widget_btn_open, getOpenPendingIntent(context));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }




    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        final String action = intent.getAction();
        if (ACTION_UPDATE_ALL.equals(action)) {
            String data= intent.getStringExtra("message");
            Log.i("outPut","deskTop data"+data);
            Toast.makeText(context,"deskTop data"+data,Toast.LENGTH_LONG).show();

        } else  {


            Log.i("outPut","deskTop data");
        }



    }


    /**
     * 获取 重置数字的广播
     */
    private static PendingIntent getBrodcastPendingIntent(Context context) {
        Intent intent=new Intent();
        intent.setAction(ACTION_UPDATE_ALL);
        intent.setPackage(context.getPackageName());
        intent.putExtra("message","From desktop dataMessage");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pi;
    }



    /**
     * 获取 打开 MainActivity 的 PendingIntent
     */
    private static PendingIntent getOpenPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityMain.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }








    /**
     * 当 widget 更新时触发，用户首次添加时也会被调用，但是如果用户定义了widget的configure属性，首次添加时不会触发，而是直接跳转
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    /**
     * 当第一个Widget的实例被创建时触发。也就是说，如果用户对同一个Widget增加了两次（两个实例），那么onEnabled()只会在第一次增加Widget时被调用
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id

        super.onDeleted(context, appWidgetIds);
    }
}

