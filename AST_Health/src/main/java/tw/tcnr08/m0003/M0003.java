package tw.tcnr08.m0003;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class M0003 extends ListActivity
{

    private String[] listFromResource;
    private ArrayList<Map<String, Object>> mList;
    private String[] health_height;
    private HashMap<String, Object> item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0003);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        listFromResource = getResources().getStringArray(R.array.m0003_semester);  //字串陣列
        mList = new ArrayList<Map<String, Object>>();
        item = new HashMap<String, Object>();
        //學期
        //身高
        health_height = getResources().getStringArray(R.array.m0003_height);
        for (int i = 0; i < listFromResource.length; i++) {
            item.put("semester", listFromResource[i]);
            item.put("height", health_height[i]);
            mList.add(item);
        }

//        for (int i = 0; i < health_height.length; i++) {
//
//            mList.add(item);
//        }

        SimpleAdapter adapter = new SimpleAdapter(this, mList,
                R.layout.health_list_item,
                new String[]{"semester","height" },
                new int[]{R.id.m0003_tt00 ,R.id.m0003_tt01 }
                );

        // "height" ,"weight" , "bmi"  , "sug" ,"vision"
        // R.id.m0003_tt01 ,  R.id.m0003_tt02 ,  R.id.m0003_tt03 ,  R.id.m0003_tt04 ,  R.id.m0003_tt005

        setListAdapter(adapter);
        //----------------------------------------------------------------
        ListView listview = getListView();
        //自動調整螢幕高度
        listview.setTextFilterEnabled(true);
        listview.setOnItemClickListener(listviewOnItemClkLis);
    }

    AdapterView.OnItemClickListener listviewOnItemClkLis = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            //mTxtResult.setText(listFromResource[position]);
        }
    };

}
