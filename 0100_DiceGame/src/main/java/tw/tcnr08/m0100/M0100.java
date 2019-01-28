package tw.tcnr08.m0100;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class M0100 extends AppCompatActivity implements View.OnClickListener , ViewSwitcher.ViewFactory
{

    private ImageSwitcher imgSwi;
    private ImageButton bigbtn , smlbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0100);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        imgSwi = (ImageSwitcher)findViewById(R.id.m0100_img01);
        bigbtn = (ImageButton)findViewById(R.id.m0100_ibn01);
        smlbtn = (ImageButton)findViewById(R.id.m0100_ibn02);

        imgSwi.setFactory(this);

        bigbtn.setOnClickListener(this);
        smlbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int random_dice = (int)(Math.random()*6+1);  //取0~1之間的值但不包含1 ,只取整數部分 , 若*6得到0~5之間的亂數 , 若*6+1得到1~6之間的亂數
        switch (random_dice){
            case 1:  //骰出1
                imgSwi.setImageResource(R.drawable.d01);  //轉換骰子1點
                break;
            case 2:
                imgSwi.setImageResource(R.drawable.d02);  //轉換骰子2點
                break;
            case 3:
                imgSwi.setImageResource(R.drawable.d03);  //轉換骰子3點
                break;
            case 4:
                imgSwi.setImageResource(R.drawable.d04);  //轉換骰子4點
                break;
            case 5:
                imgSwi.setImageResource(R.drawable.d05);  //轉換骰子5點
                break;
            case 6:
                imgSwi.setImageResource(R.drawable.d06);  //轉換骰子6點
                break;
        }
        //---------------------------
        switch (v.getId()){
            case R.id.m0100_ibn01:  //比大
                break;
            case R.id.m0100_ibn02:  //比小
                break;
        }
        //增加骰子動畫
        imgSwi.clearAnimation();
        imgSwi.setOutAnimation(AnimationUtils.loadAnimation(this , R.anim.anim_scale_rotate_out));
        imgSwi.setInAnimation(AnimationUtils.loadAnimation(this , R.anim.anim_scale_rotate_in));
    }

    @Override
    public View makeView()
    {
        ImageView v = new ImageView(this);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT));
        return v;
    }
}
