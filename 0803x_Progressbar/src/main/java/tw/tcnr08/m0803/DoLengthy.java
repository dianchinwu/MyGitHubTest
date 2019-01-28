package tw.tcnr08.m0803;

import android.os.Handler;  //mHandler永久緒, 適合android
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.util.Calendar;  //用java的

public class DoLengthy extends Thread
{
    M0803 activity;
    private Handler mHandler;
    private ProgressBar mProBar;
    private String TAG="tcnr08=>";  //生命週期
    private int lastSec=0;
    private Button button;
    private ProgressBar pb2,pb3,pb4;
    private int von=0;
    private int endvon=0;

    public void run() {
        Calendar begin = Calendar.getInstance();
        /*int a=0;
        do
        {
            if(a>60){  //大於60跳出迴圈
                break;
            }
            a+=2;
        }while (a<100);*/
        //取得開始時間

            do {
                Calendar now = Calendar.getInstance();
                final int iDiffSec = 60 * (now.get(Calendar.MINUTE)  //Different, 此迴圈經過幾秒鐘
                        - begin.get(Calendar.MINUTE)) + now.get(Calendar.SECOND)
                        - begin.get(Calendar.SECOND);  //此處改時間
//
//                if (lastSec != iDiffSec) {  //盛芃提供
//                    lastSec = iDiffSec;

                    if (iDiffSec * 20 > 100)
                    {  //調整進度條填滿速度每秒, 大於100秒結束
                        mHandler.post(new Runnable()
                        {
                            public void run()
                            {
                                mProBar.setProgress(100);  //設定layout process, 進度條100格
                                //button.setEnabled(true);  //可改while條件, ex. flag=1, 讓其可強制結束從頭開始;
                                //endvon=1;  //走完是1
//                            pb2.setVisibility(View.INVISIBLE);
//                            pb3.setVisibility(View.INVISIBLE);
//                            pb4.setVisibility(View.INVISIBLE);
                                von=1;
                                Log.d(TAG , Integer.toString(von));
                                mHandler.sendEmptyMessage(1);  //回傳von=1代表run()
                            }
                        });
                        //System.exit(0);  //結束閃退, 整個class結束
                        //break;  //萬一走到此處, 跳出迴圈
                    }
                    mHandler.post(new Runnable()
                    {
                        public void run()
                        {
                            mProBar.setProgress(iDiffSec * 20);  //調整進度條填滿速度每秒5格
                            Log.d(TAG , Integer.toString(von));
                        }
                    });
                    //secondary
                    if (iDiffSec * 50 < 100)
                        mHandler.post(new Runnable()
                        {
                            public void run()
                            {
                                mProBar.setSecondaryProgress(iDiffSec * 50);
                            }
                        });
                    else  //不可中斷跳出
                        mHandler.post(new Runnable()
                        {
                            public void run()
                            {
                                mProBar.setSecondaryProgress(100);
                            }
                        });
//                }
                    try {  //sleep抄這段
                        Thread.sleep(500);  //單位毫秒, 沒寫try會睡過頭, 沒有catch會閃退, 進度條每秒跑5%,跑20秒
                    }catch (Exception e) {
                        e.printStackTrace();  //空建構式後先寫, 失敗的話在主程式呈現=Log.d
                    }
            } while (true);  //von!=0為假的話跳出迴圈


    }

    public void setHandle(Handler h)
    {
        mHandler = h;
    }

    public void setProgressBar(ProgressBar probar)  //ProgressBar...probar  mProBar = probar[0];  接收ProgressBar陣列
    {
        mProBar = probar;  //給索引值
//        pb2 = probar[1];
//        pb3 = probar[2];
//        pb4 = probar[3];
    }

//    public void setButton(Button btn)
//    {
//        button = btn;
//    }

//    public int von(int i)
//    {
//        von = i;
//        return endvon;
//    }

//    public void von(int i)
//    {
//        von = i;
//    }
//    void setProgressBar(ProgressBar proBar) {
//        mProBar = proBar;
//    }
//
//    void setHandler(Handler h) {
//        mHandler = h;
//    }

}
