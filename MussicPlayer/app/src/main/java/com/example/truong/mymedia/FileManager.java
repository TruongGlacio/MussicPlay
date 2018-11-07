package com.example.truong.mymedia;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by truong on 12/03/2017.
 */

public class FileManager {
    Activity activity;
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getFilePathFromContentUri(Uri selectedVideoUri,ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
    private long getVideoIdFromFilePath(String filePath, ContentResolver contentResolver) {


        long videoId;
        Log.d(TAG,"Loading file " + filePath);

        // This returns us content://media/external/videos/media (or something like that)
        // I pass in "external" because that's the MediaStore's name for the external
        // storage on my device (the other possibility is "internal")
        Uri videosUri = MediaStore.Video.Media.getContentUri("external");

        Log.d(TAG,"videosUri = " + videosUri.toString());

        String[] projection = {MediaStore.Video.VideoColumns._ID};

        // TODO This will break if we have no matching item in the MediaStore.
        Cursor cursor = contentResolver.query(videosUri, projection, MediaStore.Video.VideoColumns.DATA + " LIKE ?", new String[] { filePath }, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        videoId = cursor.getLong(columnIndex);

        Log.d(TAG,"Video ID is " + videoId);
        cursor.close();
        return videoId;
    }
    public void GoHome(){

    }

}
