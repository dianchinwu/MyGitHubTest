package tw.tcnr08.m0803;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class M0803 extends AppCompatActivity implements View.OnClickListener
{

    private ProgressBar probar;
    private Button btn;
    private ProgressBar pb02;
    private ProgressBar pb03;
    private ProgressBar pb04;
    DoLengthy work=new DoLengthy();  //DoLengthy線性工作,新增一支父程式, 生成外部class, class重跑, 放btn按按鈕重新創造class
    private int chon;
    private String TAG="tcnr08=>";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0803);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        probar=(ProgressBar)findViewById(R.id.m0803_p001);
        btn=(Button)findViewById(R.id.m0803_b001);
        pb02=(ProgressBar)findViewById(R.id.m0803_p002);
        pb03=(ProgressBar)findViewById(R.id.m0803_p003);
        pb04=(ProgressBar)findViewById(R.id.m0803_p004);
        pb02.setVisibility(View.INVISIBLE);
        pb03.setVisibility(View.INVISIBLE);
        pb04.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        work=null;  //google'當不正常軟體,非法程式不能上架, 變背景批次檔
        this.finish();
    }

    @Override
    public void onClick(View v)
    {
        work.setHandle(mHandler);  //自己寫的方法
        work.setProgressBar(probar);
        //work.setProgressBar(probar,pb02,pb03,pb04);  //自己寫的方法
        //work.setButton(btn);  //自己寫的方法, 丟按鍵過去
        //chon=work.von(0);  //0代表開始執行
        work.start();
        work.checkAccess();

        }

    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what==1){
                pb02.setVisibility(View.INVISIBLE);
                pb03.setVisibility(View.INVISIBLE);
                pb04.setVisibility(View.INVISIBLE);
//                work=null;
            }else {
                pb02.setVisibility(View.VISIBLE);
                pb03.setVisibility(View.VISIBLE);
                pb04.setVisibility(View.VISIBLE);
            }
        }
    };



}
