package com.example.truong.mymedia;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class GetListMediaProsse extends Service {
    MediaManager mediaManager;
    ArrayList<String> ListName,ListPath;

    @Override
    public void onCreate() {
        super.onCreate();



    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaManager=new MediaManager();
        mediaManager.getlistMedia ();
        ListName = mediaManager.getlistName ();
        ListPath= mediaManager.getListpath ();
        saveArray (ListName,"ListName","Name");
        saveArray (ListPath,"ListPath","Path");
//        stopService (GetListMediaProsse);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        //Intent intent1=new Intent (GetListMediaProsse.this,ListMedia1.class);
      //  intent1.putStringArrayListExtra ("ListName",ListName);
        //intent1.putStringArrayListExtra ("ListPath",ListPath);

    }
    public boolean saveArray(ArrayList<String> sKey,String ListNameSize,String nameMember)
    {SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();

    /* sKey is an array */
        mEdit1.putInt(ListNameSize, sKey.size());

        for(int i=0;i<sKey.size();i++)
        {
            mEdit1.remove(nameMember + i);
            mEdit1.putString(nameMember + i, sKey.get(i));
        }

        return mEdit1.commit();
    }
}
