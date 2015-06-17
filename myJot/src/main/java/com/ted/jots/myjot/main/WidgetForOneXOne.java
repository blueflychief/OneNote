package com.ted.jots.myjot.main;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import com.ted.jots.myjot.R;
import com.ted.jots.myjot.config.ConfigManager;
import com.ted.jots.myjot.data.DataManager;

/**
 * Created by Ted on 2015/2/14.
 */
public class WidgetForOneXOne extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ConfigManager configManager = new ConfigManager(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetForOneXOne.class));
        int num = widgetIds.length;
        for (int i = 0; i < num; i++) {
            try {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout_onexone);
                DataManager dataManager = new DataManager(context);
                remoteViews.setTextViewText(R.id.widget_update, dataManager.readData().getContent());
                remoteViews.setInt(R.id.widget_layout,"setBackgroundColor",configManager.getConfigWidgetBgColor());
                remoteViews.setOnClickPendingIntent(R.id.widget_layout, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
                ComponentName componentName = new ComponentName(context, WidgetForOneXOne.class);
                AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
            } catch (Exception e) {

            }

        }
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (null == intent.getAction()
                || !intent.getAction().equalsIgnoreCase("android.appwidget.action.APPWIDGET_SET_DATA"))return;
        ConfigManager configManager = new ConfigManager(context);
        ComponentName componentName = new ComponentName(context, WidgetForOneXOne.class);
        int[] widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(componentName);
        int num = widgetIds.length;
        for (int i = 0; i < num; i++) {
            try {
                String str = intent.getExtras().getString("TEXT_STRING");
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout_onexone);
                remoteViews.setTextViewText(R.id.widget_update, str);
                remoteViews.setInt(R.id.widget_layout,"setBackgroundColor",configManager.getConfigWidgetBgColor());
                remoteViews.setOnClickPendingIntent(R.id.widget_layout, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
                AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
            }catch (Exception e){
                try {
                    DataManager dataManager = new DataManager(context);
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetlayout_onexone);
                    remoteViews.setTextViewText(R.id.widget_update, dataManager.readData().getContent());
                    remoteViews.setInt(R.id.widget_layout,"setBackgroundColor",configManager.getConfigWidgetBgColor());
                    remoteViews.setOnClickPendingIntent(R.id.widget_layout, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
                    AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
                } catch (Exception ex) {

                }
            }
        }
    }

}
