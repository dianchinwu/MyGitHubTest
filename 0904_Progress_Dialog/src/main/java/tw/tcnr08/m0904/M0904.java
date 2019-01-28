package tw.tcnr08.m0904;

//import android.app.ProgressDialog;
//import android.app.Service;
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.os.Vibrator;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import java.util.Calendar;
//
//public class M0904 extends AppCompatActivity implements View.OnClickListener , SensorEventListener
//{
//
//    private Button btn;
//    private Handler mHandler =new Handler();  //new抄一個過來改 , 設定 , 原來屬性重新定義
//    private int lastSec=0;
//    private SensorManager mSensorManager;
//    private Sensor mAccelerometer;
//    private M0904 mShakeDetector;
//    private Vibrator mVibrator;
//    private OnShakeListener mListener;
//    private int  mShakeCount=0 , t ;
//    private  long firstclick=0 , secondclick=0;
//    private float ShakeThresholdGravity=2.5f;  //重力閥值 , 甩一次多大力
//    private long mShakeTimestamp;  //毫秒 , 開始搖動的時間
//    private int ShakeSlopTimeMs=500;  //每次甩動間隔在0.5秒間才算數
//    private int ShakeCountResetTimeMs=5000;  //5秒沒甩則重新計算
//    private int iDiffSec=0;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.m0904);
//        setupViewComponent();
//    }
//
//    private void setupViewComponent()
//    {
//        btn=(Button)findViewById(R.id.m0904_b001);
//        btn.setOnClickListener(this);
//        //------------------------------
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //開啟感測服務(包含感光,感聲,陀螺儀)
//        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  //將加速度計設定給mAccelerometer使用(抓出來)
//        mShakeDetector = new M0904();  //mShakeDetector繼承M0904這個class(第一次用在layout看不到的物件),還是要設定與監聽
//        mShakeDetector.setOnShakeListener(new OnShakeListener() {  //.setOnShakeListener({});  前面setOnShakeListener() . 後面interface setOnShakeListener
//            @Override
//            public void onShake(int count) {
//                mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//取得系統Vibrator服務控制
//                mVibrator.vibrate(1000);//震動一秒
////                Toast.makeText(getApplicationContext(), "Shake:" + count, Toast.LENGTH_SHORT).show();
//                if (count % 3 == 0) {
//                    domywork();
//                }
//            }
//        });
//    }
//
//    private void domywork()
//    {
//        //設定監聽震動處理進度的方法
//        final ProgressDialog pd =new ProgressDialog(M0904.this);  //new在哪邊
//        pd.setTitle(getString(R.string.m0904_title));
//        pd.setMessage(getString(R.string.m0904_message));
//        pd.setIcon(android.R.drawable.btn_star_big_on);
//        pd.setCancelable(true);
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  //水平進度條
//        pd.setMax(100);  //進度條最大值
//        pd.show();
//
//        //可抄M0803
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Calendar begin=Calendar.getInstance();
//                do {  //用資料庫的資料去算, 用next ,一筆一個進度
//                    Calendar now = Calendar.getInstance();
//                    iDiffSec = 60 * (now.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE)) +
//                            now.get(Calendar.SECOND) - begin.get(Calendar.SECOND);
//                    //因每秒thread 跑不止一次, 所以要寫lastSec才不會有問題
//
//                    //if (lastSec != iDiffSec) {  //盛芃提供 , 不同秒數才執行一次
//                    //    lastSec = iDiffSec;
//
//                    if (iDiffSec * 10 > 100) {
//                        mHandler.post(new Runnable() {
//                            public void run() {
//                                pd.setProgress(100);
//                            }
//                        });
//                        break;
//                    }
//                    mHandler.post(new Runnable() {
//                        public void run() {
//                            pd.setProgress(iDiffSec * 10);
//                        }
//                    });
//
//                    if ((iDiffSec * 10)+2 < 100)
//                        mHandler.post(new Runnable() {
//                            public void run() {
//                                pd.setSecondaryProgress(iDiffSec * 10);
//                            }
//                        });
//                    else
//                        mHandler.post(new Runnable() {
//                            public void run() {
//                                pd.setSecondaryProgress(100);
//                            }
//                        });
//                    //    }
//                    try  //放此處在do迴圈做完一次後睡9.99秒, Surround with try/catch
//                    {
//                        Thread.sleep(999);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }while (true);  //加分號
//
//                pd.cancel();  //不跑收起來
//                //---------------------------
//                firstclick=0;
//                secondclick=0;
//                mShakeCount=0;
//                t=1;
////-------------------------------------
//            }
//
//        }).start() ;  //Runnable開始跑
//    }
//
//    @Override
//    public void onClick(View v)
//    {
//        if (SystemClock.elapsedRealtime() - firstclick<2000){
//            if (SystemClock.elapsedRealtime() - secondclick <2000){
//                domywork();
//                return;
//            }
//            secondclick = SystemClock.elapsedRealtime();
//            return;
//        }
//        firstclick = SystemClock.elapsedRealtime();
////        if (SystemClock.elapsedRealtime() - firstclick < 2000) {
////            if (SystemClock.elapsedRealtime() - secondclick < 2000) {
////                domywork();
////                return;
////            }
////            secondclick = SystemClock.elapsedRealtime();
////            return;
////        }
////        firstclick = SystemClock.elapsedRealtime();
////    }
//
////        final ProgressDialog pd =new ProgressDialog(M0904.this);  //new在哪邊
////        pd.setTitle(getString(R.string.m0904_title));
////        pd.setMessage(getString(R.string.m0904_message));
////        pd.setIcon(android.R.drawable.btn_star_big_on);
////        pd.setCancelable(true);
////        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  //水平進度條
////        pd.setMax(100);  //進度條最大值
////        pd.show();
//
////        //可抄M0803
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                Calendar begin=Calendar.getInstance();
////                do
////                {  //用資料庫的資料去算, 用next ,一筆一個進度
////                    Calendar now = Calendar.getInstance();
////                    final int iDiffSec = 60 * (now.get(Calendar.MINUTE) - begin.get(Calendar.MINUTE)) +
////                            now.get(Calendar.SECOND) - begin.get(Calendar.SECOND);
////                    //因每秒thread 跑不止一次, 所以要寫lastSec才不會有問題
////
////                    //if (lastSec != iDiffSec) {  //盛芃提供 , 不同秒數才執行一次
////                    //    lastSec = iDiffSec;
////
////                    if (iDiffSec * 20 > 100)
////                    {
////                        mHandler.post(new Runnable()
////                        {
////                            public void run()
////                            {
////                                pd.setProgress(100);
////                            }
////                        });
////                        break;
////                    }
////
////                    mHandler.post(new Runnable()
////                    {
////                        public void run()
////                        {
////                            pd.setProgress(iDiffSec * 20);
////                        }
////                    });
////
////                    if (iDiffSec * 5 < 100)
////                        mHandler.post(new Runnable()
////                        {
////                            public void run()
////                            {
////                                pd.setSecondaryProgress(iDiffSec * 5);
////                            }
////                        });
////                    else
////                        mHandler.post(new Runnable()
////                        {
////                            public void run()
////                            {
////                                pd.setSecondaryProgress(100);
////                            }
////                        });
////                //    }
////                    try  //放此處在do迴圈做完一次後睡9.99秒, Surround with try/catch
////                    {
////                        Thread.sleep(999);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////
////                }while (true);  //加分號
////
////            pd.cancel();  //不跑收起來
////
////            }
////
////        }).start() ;  //Runnable開始跑
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event)
//    {
//        if (mListener != null) {  //有動作的話
//            float x = event.values[0];  // x軸回傳值
//            float y = event.values[1];  // y軸回傳值
//            float z = event.values[2];  // z軸回傳值
//
//            float gX = x / SensorManager.GRAVITY_EARTH;  //地軸, 算sita值 , 算角度
//            float gY = y / SensorManager.GRAVITY_EARTH;
//            float gZ = z / SensorManager.GRAVITY_EARTH;
//
//            // gForce will be close to 1 when there is no movement.
//
////            float gForce = FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ);
//            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);  //float值
//            if (gForce > ShakeThresholdGravity) {  //假如使出的力量大於設定的力量2.5磅
//                final long now = System.currentTimeMillis();
//                // ignore shake events too close to each other (500ms)
//                if (mShakeTimestamp + ShakeSlopTimeMs > now) {
//                    return;  //再來一次
//                }
//                // reset the shake count after 3 seconds of no shakes
//                if (mShakeTimestamp + ShakeCountResetTimeMs < now) {
//                    mShakeCount = 0;
//                }
//
//                mShakeTimestamp = now;
//                mShakeCount++;
//
//                mListener.onShake(mShakeCount);
//            }
//        }
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy)
//    {
//
//    }
//    //<!---------------自建立搖動監聽器----------------------->
//    private interface OnShakeListener {
//        //宣告一個發生搖動時候的監聽器
//        public void onShake(int count);
//    }
//
//    public void setOnShakeListener(OnShakeListener listener) {  //宣告設定搖動監聽器的method
//        this.mListener = listener;
//    }
////------------加系統方法-------------
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//    }
//}

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class M0904 extends AppCompatActivity implements SensorEventListener {

    private Button B001;
    private long firstclick = 0, secondclick = 0;
    private int iDiffSec = 1;
    private Handler proBarHandler = new Handler();
    private int t;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private M0904 mShakeDetector;
    private Vibrator mVibrator;
    //   <!--------設定G力所使用到的變數--------->
    private static final float ShakeThresholdGravity = 2.7f;//重力閾值  1次要甩多大力
    private static final int ShakeSlopTimeMs = 500; //甩1次要在0.5秒內完成才算1次
    private static final int ShakeCountResetTimeMs = 3000;//多久沒甩 計數器會重製
    //<----------------使用加速度計會使用到的變數------------------->
    private OnShakeListener mListener;//搖動監聽器
    private long mShakeTimestamp;//暫存開始搖動的系統時間
    private int mShakeCount = 0;//紀錄搖動次數的計數器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0904);
        setupviewcomponent();
    }
    private void setupviewcomponent() {
        B001=(Button)findViewById(R.id.m0904_b001);
        B001.setOnClickListener(B001on);

        //--------------------------------------------------------------------
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//開啟感測服務
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//將加速度計設定給mAccelerometer使用
        mShakeDetector = new M0904();
//mShakeDetector繼承M0904這個class
        mShakeDetector.setOnShakeListener(new OnShakeListener() {

            @Override
            public void onShake(int count) {
                mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//取得系統Vibrator服務控制
                mVibrator.vibrate(1000);//震動一秒
                Toast.makeText(getApplicationContext(),"Shake:"+count,Toast.LENGTH_SHORT).show();
                if(count%3==0){
                    domywork();
                }
            }
        });
    }

    private Button.OnClickListener B001on = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (SystemClock.elapsedRealtime() - firstclick < 2000) {
                if (SystemClock.elapsedRealtime() - secondclick < 2000) {
                    domywork();
                    return;
                }
                secondclick = SystemClock.elapsedRealtime();
                return;
            }
            firstclick = SystemClock.elapsedRealtime();
        }
    };

    protected void domywork() {
        final ProgressDialog proBarDialog = new ProgressDialog(M0904.this);
        proBarDialog.setTitle(getString(R.string.m0904_title));
        proBarDialog.setMessage(getString(R.string.m0904_message));
        proBarDialog.setIcon(android.R.drawable.star_big_on);
        proBarDialog.setCancelable(false);
        proBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        proBarDialog.setMax(100);
        proBarDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Calendar begin = Calendar.getInstance();
                do {
                    Calendar now = Calendar.getInstance();
                    iDiffSec = (60 * now.get(Calendar.MINUTE) - 60 * begin.get(Calendar.MINUTE))
                            + (now.get(Calendar.SECOND) - begin.get(Calendar.SECOND));
                    if (iDiffSec * 10 > 100) {
                        proBarHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                proBarDialog.setProgress(100);
                            }
                        });
                        break;
                    }
                    proBarHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            proBarDialog.setProgress(iDiffSec * 10);
                        }
                    });
                    //---------------------------------
                    if ((iDiffSec * 10) + 2 < 100) {
                        proBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                proBarDialog.setSecondaryProgress((iDiffSec * 10) +2);
                            }
                        });
                    } else
                        proBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                proBarDialog.setSecondaryProgress(100);
                            }
                        });
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                } while (true);

                proBarDialog.cancel();
                firstclick = 0;
                secondclick = 0;
                t = 1;
                mShakeCount = 0;
            }
        }).start();
    }

    //<!---------------自建立搖動監聽器----------------------->
    private interface OnShakeListener {
        //宣告一個發生搖動時候的監聽器
        public void onShake(int count);
    }

    public void setOnShakeListener(OnShakeListener listener) {
//宣告設定搖動監聽器的method
        this.mListener = listener;
    }

    //<---------------------------------------------------->
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.

//            float gForce = FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ);
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
            if (gForce > ShakeThresholdGravity) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + ShakeSlopTimeMs > now) {
                    return;
                }
                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + ShakeCountResetTimeMs < now) {
                    mShakeCount = 0;
                }

                mShakeTimestamp = now;
                mShakeCount++;

                mListener.onShake(mShakeCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        super.onResume();
//在resume週期的時候持續監聽
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
//在pause週期的時候停止監聽
        super.onPause();
    }
}
