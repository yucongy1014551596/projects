package yucong.example.com.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    // 更新 widget 的广播对应的action
    private final static String ACTION_UPDATE_ALL = "com.lyl.widget.UPDATE_ALL";
    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。

    public static int mIndex;



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        views.setTextViewText(R.id.widget_txt, String.valueOf(mIndex));
        // 设置点击按钮对应的PendingIntent：即点击按钮时，发送广播。
        views.setOnClickPendingIntent(R.id.widget_btn_reset, getResetPendingIntent(context));
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
            // “更新”广播
        } else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
            // “按钮点击”广播
            mIndex = 0;
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
//        Log.i("d",getPackageName());
        intent.putExtra("message","From desktop dataMessage");
//        intent.setComponent(new ComponentName(" yucong.example.com.widget","yucong.example.com.widget.MyAppWidget"));
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pi;
    }



    /**
     * 获取 打开 MainActivity 的 PendingIntent
     */
    private static PendingIntent getOpenPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("main", "这句话是我从桌面点开传过去的。");
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
//         There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        // 每次 widget 被创建时，对应的将widget的id添加到set中

    }

    /**
     * 当第一个Widget的实例被创建时触发。也就是说，如果用户对同一个Widget增加了两次（两个实例），那么onEnabled()只会在第一次增加Widget时被调用
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // 在第一个 widget 被创建时，开启服务

        super.onEnabled(context);
    }


    // 当 widget 被初次添加 或者 当 widget 的大小被改变时，被调用
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle
            newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id

        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 类似onEnabled()方法，不过是在最后一个Widget被删除时调用
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // 在最后一个 widget 被删除时，终止服务
//        Intent intent = new Intent(context, WidgetService.class);

        super.onDisabled(context);

    }
}

