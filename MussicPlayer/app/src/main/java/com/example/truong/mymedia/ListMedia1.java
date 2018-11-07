package com.example.truong.mymedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListMedia1 extends AppCompatActivity {
  //  MediaManager mediaManager;

    ArrayList<String> ListName,ListPath;
  //  Handler mhHandlerName,mhHandlerPath;
    //MainActivity mainActivity;
    private android.util.Log log;
    Intent mIntent=getIntent ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_media1);

        final ListView listView = (ListView) findViewById(R.id.listView);
        ListName=null;
        ListPath=null;
        loadArray (ListMedia1.this,ListName,"ListName","Name");
        loadArray (ListMedia1.this,ListPath,"ListPath","Path");
      //  mediaManager=new MediaManager();
      //  RunProgressBar();

       // mediaManager.getlistMedia();
   //     ListName=mIntent.getStringArrayListExtra ("ListName");//mediaManager.getlistName();
     //   ListPath=mIntent.getStringArrayListExtra ("ListPath");//mediaManager.getListpath();
        Toast.makeText(getBaseContext(),ListName+"Sellec",Toast.LENGTH_SHORT).show();

       if(ListName!=null)
       {
           List<Medialistview> image_details = getListData(ListName,ListName);
           listView.setAdapter(new ListMediaAdapter(this, image_details));

           // Khi người dùng click vào các ListItem
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

               @Override
               public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                   Object o = listView.getItemAtPosition(position);
                   Medialistview country = (Medialistview) o;
                   Toast.makeText(ListMedia1.this, "Selected :" + " " + country.getPath().toString(), Toast.LENGTH_LONG).show();
                   String uri= country.getPath().toString();

                   Intent intent=new Intent(ListMedia1.this,MainActivity.class);
                   intent.putExtra("uri",uri);
                   startActivity(intent);
               }
           });

       }
       else {
           Toast.makeText(ListMedia1.this, "Not Data", Toast.LENGTH_LONG).show();

       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Intent intent=new Intent(ListMedia1.this,MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
    }
    //show ListMaedi to listview
    private List<Medialistview> getListData(ArrayList<String> listName, ArrayList<String> listPath) {
        List<Medialistview> list = new ArrayList<Medialistview>();


        if (listName != null && listPath != null) {

            try {
                for (int i = 0; i < listName.size(); i++) {
                    Medialistview mediainfo= new Medialistview(listName.get(i),listPath.get(i), R.drawable.abum);
                    log.i("file details "," name ="+listName.get(i) +" path = "+listPath.get(i));
                    list.add(mediainfo);
                }
                Medialistview mediainfo1 = new Medialistview("abc", "abc1", R.drawable.abum);
                list.add(mediainfo1);

            }
            catch (Exception e) {
                Toast.makeText(getBaseContext(), "Failes to get name media", Toast.LENGTH_SHORT).show();
            }
            //      }
            //    else {
            Medialistview mediainfo1 = new Medialistview("Not find mp3 file", "Not find mo3 file", R.drawable.abum);
            list.add(mediainfo1);

        }

        return list;
    }
    public void loadArray(Context mContext, ArrayList<String> sKey,String ListNameSize,String nameMember)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
//        sKey.clear();
        int size = mSharedPreference1.getInt(ListNameSize, 0);

       if(mSharedPreference1!=null)
       {
           for(int i=0;i<size;i++)
           {
               sKey.add(mSharedPreference1.getString(nameMember + i, null));
           }

       }
    }

}
