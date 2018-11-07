package com.example.truong.mymedia;

import android.provider.ContactsContract;

import java.util.IdentityHashMap;

/**
 * Created by truong on 14/03/2017.
 */

public class Medialistview {
    private String name;
    private String path;
    private int im;
    public Medialistview(String Name,String Path,int Im){
        this.name=Name;
        this.path=Path;
        this.im=Im;
    }
    public String getName(){
        return name;
    }
    public void setName(String Name){
        this.name=Name;
    }
    public String getPath(){
        return path;
    }
    public void setPath(String Path){
        this.path=Path;
    }
    public int getIm(){
        return  im;
    }
    public void setIm(int Im){
        this.im=Im;
    }

    @Override
    public String toString() {
        return this.name+ "(Name"+this.path+"path)";
    }
}
