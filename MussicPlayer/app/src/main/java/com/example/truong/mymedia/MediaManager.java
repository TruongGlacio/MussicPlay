package com.example.truong.mymedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by truong on 12/03/2017.
 */
public class MediaManager {
    public MediaPlayer mediaPlayer=new MediaPlayer();
    public ArrayList<HashMap<String,String>> songList, songlist2;
    public ArrayList<String> Listname=new ArrayList<String>();
    public  ArrayList<String> ListPath=new ArrayList<String>();
    private android.util.Log log;


    public void creatMedia(Context a, int uri){
        try{
            mediaPlayer=MediaPlayer.create(a,uri);
        }
        catch (Exception e){

        }

    }
    public void creatmediaUri(Context a, Uri uri)
    {
        try{
            mediaPlayer=MediaPlayer.create(a,uri);
        }
        catch (Exception e){

        }
    }
    public void StartMedia(){


       try{
           mediaPlayer.start();



       }
       catch (Exception e){

       }

    }
    public void PauseMedia(Context a){

       try{
             mediaPlayer.pause();


               Toast.makeText(a,"Paused",Toast.LENGTH_SHORT).show();
       }
       catch (Exception e){
           Toast.makeText(a,"Can't Pause",Toast.LENGTH_SHORT).show();

       }



    }
    public void NextMedia(Context context, double finalTime,double startTime, double forwardTime){
        int temp = (int)startTime;

        if((temp+forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
            Toast.makeText(context,"Forward 5 seconds",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(context,"Can't next ",Toast.LENGTH_SHORT).show();
        }
    }
    public void  BackMedia(Context a,double startTime,double backwardTime){
        int temp = (int)startTime;

        if((temp-backwardTime)>0){
            try{
                startTime = startTime - backwardTime;
                mediaPlayer.seekTo((int) startTime);
                Toast.makeText(a,"Backward 5 seconds",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){}
        }else{
            Toast.makeText(a,"Error: Can't backward ",Toast.LENGTH_SHORT).show();
        }
    }
    public void Nextother(){

    }
    public  void  Backother(){}

    public  void getlistMedia(){
        String rootPath;
        ArrayList<HashMap<String,String>> songList, songlist2;

        songList=getPlayList("/storage/sdcard1/");
  //      songlist2=getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath() );

        if(songList!=null){
            for(int i=0;i<songList.size();i++){
                String fileName=songList.get(i).get("file_name");
                String filePath=songList.get(i).get("file_path");

                Listname.add(fileName);
                ListPath.add(filePath);
                getlistName();
                getListpath();


                //here you will get list of file name and file path that present in your device
             //   log.d("file details "," name ="+Listname.get(i) +" path = "+ListPath.get(i));
            }
            /*for (int i=songList.size();i<songList.size()+songlist2.size();i++)
            {
                String fileName=songlist2.get(i).get("file_name");
                String filePath=songlist2.get(i).get("file_path");
                Listname.add(fileName);
                ListPath.add(filePath);
            }
            getlistName();
            getListpath();*/
        }

    }
    public ArrayList<String> getlistName(){
        ArrayList<String> listname;
        listname=Listname;
        return  listname;
    }
    public ArrayList<String> getListpath(){
        ArrayList<String> listpath;
        listpath=ListPath;
        return listpath;
    }

    ArrayList<HashMap<String,String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();


        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
           // files.setType("audio/mpeg");

            //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }

    public void ListHistory(){}
    public void Abum(){}


}
