package tw.tcnr08.m1001;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class M1001 extends AppCompatActivity implements View.OnClickListener
{
    private Button b001, b002, b003, b004, b005, b006, b007, b008, b009, b010;
    private Uri uri;
    private Intent it;
    private File file;
    private String TAG = "tcnr08=>";
    private String[] sdcardpath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1001);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        sdcardpath = getExternalStorageDirectories();
        b001 = (Button) findViewById(R.id.m1001_b001);
        b002= (Button) findViewById(R.id.m1001_b002);
        b003= (Button) findViewById(R.id.m1001_b003);
        b004= (Button) findViewById(R.id.m1001_b004);
        b005 = (Button) findViewById(R.id.m1001_b005);
        b006= (Button) findViewById(R.id.m1001_b006);
        b007= (Button) findViewById(R.id.m1001_b007);
        b008= (Button) findViewById(R.id.m1001_b008);
        b009= (Button) findViewById(R.id.m1001_b009);
        b010= (Button) findViewById(R.id.m1001_b010);

        b001.setOnClickListener(this);
        b002.setOnClickListener(this);
        b003.setOnClickListener(this);
        b004.setOnClickListener(this);
        b005.setOnClickListener(this);
        b006.setOnClickListener(this);
        b007.setOnClickListener(this);
        b008.setOnClickListener(this);
        b009.setOnClickListener(this);
        b010.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //超連結
            case R.id.m1001_b001:
                uri = Uri.parse("http://developer.android.com/");
                it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            //播放音樂
            case R.id.m1001_b002:
                it = new Intent();
                it.setAction(android.content.Intent.ACTION_VIEW);
                try
                {
                    Uri uri = Uri.parse("file:///sdcard/tcnr/song.mp3");//替換成audiopath , 設定資料夾絕對路徑
                    it.setDataAndType(uri, "audio/*");
                    startActivity(it);
                } catch (Exception e)
                {
                    Toast.makeText(getApplication(), "file not found", Toast.LENGTH_LONG).show();
                }
                break;
            //顯示圖片
            case R.id.m1001_b003:
                it = new Intent(Intent.ACTION_VIEW);  //new Intent加入參數 , 直接打開指定檔案圖片(file)
                try {
                    file = new File(sdcardpath[0] + "/tcnr/paperfish520.png");
                    Log.d(TAG,sdcardpath[0] + "/tcnr/paperfish520.png");
                    it.setDataAndType(Uri.fromFile(file), "image/*");
                    startActivity(it);
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "file not found", Toast.LENGTH_LONG).show();
                }
                break;
            //撥出電話
            case R.id.m1001_b004:
                uri = Uri.parse("tel:0800080123");
                it = new Intent(Intent.ACTION_DIAL , uri);
                //Intent it = new Intent(Intent.ACTION_CALL, uri); 直接打電話出去
                startActivity(it);
                break;
            //顯示地圖
            case R.id.m1001_b005:
                uri = Uri.parse("geo:24.1608599,120.6461485");  //台中市政府
                //geo:38.899533,-77.036476 美國白宮
                //geo:24.1704367,120.6078173  勞動力發展署中彰投分署
                it = new Intent(Intent.ACTION_VIEW , uri);
                startActivity(it);
                break;
            //路徑規劃
            case R.id.m1001_b006:
                uri =Uri.parse("https://goo.gl/maps/zRa63wAA15B2");
                it = new Intent(Intent.ACTION_VIEW , uri);
                startActivity(it);
                break;
            //傳送email
            case R.id.m1001_b007:
                uri = Uri.parse("mailto:dianchin3511@gmail.com");
                //mailto =new Intent(Intent.ACTION_SENDTO , uri);
                //startActivity(mailto);
                it = new Intent(Intent.ACTION_SEND);
                String[] mailto ={"dianchin3511@gmail.com"};  //需以陣列紀錄字串內容, 再丟到EXTRA_EMAIL後面的參數
                it.putExtra(Intent.EXTRA_EMAIL , mailto);
                it.putExtra(Intent.EXTRA_TEXT , "有任何問題歡迎聯繫我們");
                it.setType("text/plain");
                startActivity(it);
//                Intent it=new Intent(Intent.ACTION_SEND);
//                String[] tos={"me@abc.com"};
//                String[] ccs={"you@abc.com"};
//                it.putExtra(Intent.EXTRA_EMAIL, tos);  //收件者
//                it.putExtra(Intent.EXTRA_CC, ccs);  //副本收件者
//                it.putExtra(Intent.EXTRA_TEXT, "The email body text");  //信件內容
//                it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");  //信件主旨
//                it.setType("message/rfc822");  //參考網址http://hant.ask.helplib.com/android/post_1304838
//                startActivity(Intent.createChooser(it, "Choose Email Client"));
                break;
            //顯示聯絡人清單
            case R.id.m1001_b008:
                it = new Intent(Intent.ACTION_VIEW , ContactsContract.Contacts.CONTENT_URI);
                startActivity(it);
                break;
            //啟動照相機，並將相片存在指定的檔案中
            case R.id.m1001_b009:
                it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //假設你要將相片存在 /sdcard/xxx.jpg 中
                String imgName = System.currentTimeMillis() + ".jpg";
                try{
                    File saveimg = new File(sdcardpath[0] +"/tcnr/" + imgName );
                    Log.d(TAG , sdcardpath[0] +"/tcnr/" + imgName);
                    it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveimg));
                    startActivity(it);
                }catch (Exception e){
                     Toast.makeText(getApplication(), "file not found", Toast.LENGTH_LONG).show();
               }
//               try
//               {
//                   Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                   photoPickerIntent.setType("image/*");
//                   startActivity(photoPickerIntent);
//               }catch (Exception e){
//                   Toast.makeText(getApplication(), "image not found", Toast.LENGTH_LONG).show();
//               }
               break;
            //Google Play(Hairykid)
            case R.id.m1001_b010:
                uri = Uri.parse("market://details?id=com.itri.petcloud&hl=zh-TW");  //路徑寫法  ://details?id=pkg_name_or_app_id
                it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                //where app_id is the application ID, find the ID
                //by clicking on your application on Market home
                //page, and notice the ID from the address bar
                break;
        }
    }

    public String[] getExternalStorageDirectories() {

        List<String> results = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //Method 1 for KitKat & above
            File[] externalDirs = getExternalFilesDirs(null);

            for (File file : externalDirs) {
                String path = file.getPath().split("/Android")[0];

                boolean addPath = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addPath = Environment.isExternalStorageRemovable(file);
                } else {
                    addPath = Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(file));
                }

                if (addPath) {
                    results.add(path);
                }
            }
        }

        if (results.isEmpty()) { //Method 2 for all versions
            // better variation of: http://stackoverflow.com/a/40123073/5002496
            String output = "";
            try {
                final Process process = new ProcessBuilder().command("mount | grep /dev/block/vold")
                        .redirectErrorStream(true).start();
                process.waitFor();
                final InputStream is = process.getInputStream();
                final byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    output = output + new String(buffer);
                }
                is.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if (!output.trim().isEmpty()) {
                String devicePoints[] = output.split("\n");
                for (String voldPoint : devicePoints) {
                    results.add(voldPoint.split(" ")[2]);
                }
            }
        }

        //Below few lines is to remove paths which may not be external memory card, like OTG (feel free to comment them out)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().matches(".*[0-9a-f]{4}[-][0-9a-f]{4}")) {
                    Log.d(TAG, results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        } else {
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).toLowerCase().contains("ext") && !results.get(i).toLowerCase().contains("sdcard")) {
                    Log.d(TAG, results.get(i) + " might not be extSDcard");
                    results.remove(i--);
                }
            }
        }

        String[] storageDirectories = new String[results.size()];
        for (int i = 0; i < results.size(); ++i) storageDirectories[i] = results.get(i);

        return storageDirectories;
    }

}
