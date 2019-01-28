package tw.tcnr08.m0100;

import android.app.ListActivity;
import android.os.Bundle;

public class List_Item extends ListActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        setupViewComponent();
    }

    private void setupViewComponent()
    {

    }
}
