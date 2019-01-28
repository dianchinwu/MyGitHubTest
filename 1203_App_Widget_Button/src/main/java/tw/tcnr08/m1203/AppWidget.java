package tw.tcnr08.m1203;

import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {

    private final String TAG = "tcnr08=>";
    private static AlarmManager mAlarmManager;
    private static PendingIntent mPendingIntent;


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted()");
        mAlarmManager.cancel(mPendingIntent);
    }

    @Override
    public void onDisabled(Context context) {
        // TODO Auto-generated method stub
        super.onDisabled(context);
        Log.d(TAG, "onDisabled()");
    }

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        super.onEnabled(context);
        Log.d(TAG, "onEnabled()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if(!intent.getAction().equals("tw.tcnr08.MY_OWN_WIDGET_UPDATE"))
            return;

        Log.d(TAG, "onReceive()");

        AppWidgetManager appWidgetMan = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(),
                AppWidget.class.getName());
        int[] appWidgetIds = appWidgetMan.getAppWidgetIds(thisAppWidget);

        onUpdate(context, appWidgetMan, appWidgetIds);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            remoteViews.setTextViewText(R.id.m1203_c001, number);

            Intent intent = new Intent(context, AppWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.m1203_b001, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate()");
    }


    static void SaveAlarmManager(AlarmManager alarmManager, PendingIntent pendingIntent)
    {
        mAlarmManager = alarmManager;
        mPendingIntent = pendingIntent;
    }

}