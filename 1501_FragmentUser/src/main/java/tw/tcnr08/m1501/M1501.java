package tw.tcnr08.m1501;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tw.tcnr08.m1501.ui.m1501.M1501Fragment;

public class M1501 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1501);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, M1501Fragment.newInstance())
                    .commitNow();
        }
    }
}
