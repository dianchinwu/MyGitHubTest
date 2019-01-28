package tw.tcnr08.m1203;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;

public class M1203 extends AppCompatActivity
{
    private int mAppWidgetId;
    private final String TAG = "tcnr08=>";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1203);

        Intent itIn = getIntent();
        Bundle extras = itIn.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        Intent itOut = new Intent("tw.tcnr08.MY_OWN_WIDGET_UPDATE");
        PendingIntent penIt = PendingIntent.getBroadcast(this, 0, itOut, 0);
        AlarmManager alarmMan = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);
        alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 20*1000, penIt);

        AppWidget.SaveAlarmManager(alarmMan, penIt);

        Intent itAppWidgetConfigResult = new Intent();
        itAppWidgetConfigResult.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, itAppWidgetConfigResult);

        finish();

    }


}
