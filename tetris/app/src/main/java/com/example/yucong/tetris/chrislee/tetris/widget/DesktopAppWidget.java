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
        views.setOnClickPendingIntent(R.id.widget_sendBrodcast, getResetPendingIntent(context));
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
            // “更新”广播
        } else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
            // “按钮点击”广播

            Log.i("outPut","deskTop data");
        }



    }






    /**
     * 获取 重置数字的广播
     */
    private static PendingIntent getResetPendingIntent(Context context) {
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
        intent.putExtra("main", "这句话是我从桌面点开传过去的。");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }














    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

