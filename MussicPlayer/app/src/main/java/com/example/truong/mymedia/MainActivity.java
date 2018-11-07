package com.example.truong.mymedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG="d";
    FloatingActionButton Play_Pause, next, back, nextother,backother;
    TextView currentTime_text, endTime_text;
    private SeekBar seekBar;
    ImageView avata;
    private int progressBarStatus = 0;
   private ProgressDialog progressBar;
   private Handler progressBarbHandler = new Handler();
    boolean getlistdone=false;


    public Uri uri;

    private double startTime=0,finaltime=0,forwardtime=5000,backwardtime=5000;
    public static int oneTimeOnly = 0;

    Boolean a=false;
    private Handler myHandler = new Handler();;
    MediaManager mediaManager=new MediaManager();


    FileManager fileManager=new FileManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(getBaseContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Anhxa();
        Intent i=getIntent();
        String uriFromListMedia1= i.getStringExtra("uri");
        mediaManager.mediaPlayer=new MediaPlayer();

        if(uriFromListMedia1!=null)
        {
            uri= Uri.parse(uriFromListMedia1);
            mediaManager.creatmediaUri(MainActivity.this,uri);

        }
        else {
            mediaManager.creatMedia(MainActivity.this,R.raw.neumainay);

        }

        Play_Pause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            a=!a;
            if(a==true)
            {
                mediaManager.StartMedia();
                finaltime=mediaManager.mediaPlayer.getDuration();
                startTime=mediaManager.mediaPlayer.getCurrentPosition();
                endTime_text.setText(String.format(" %d min:%d s",
                        TimeUnit.MILLISECONDS.toMinutes((long) finaltime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finaltime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finaltime))));
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                avata.startAnimation(animation1);

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finaltime);
                    oneTimeOnly = 1;
                }

                seekBar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
            }
            else {
                mediaManager.PauseMedia(MainActivity.this);
            }
        }
    });
    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mediaManager.NextMedia(MainActivity.this,finaltime,startTime,forwardtime);
        }
    });
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mediaManager.BackMedia(MainActivity.this,startTime,backwardtime);
        }
    });
    }

private void Anhxa(){
    Play_Pause=(FloatingActionButton)findViewById(R.id.fab_play);
    next=(FloatingActionButton) findViewById(R.id.fab_next);
    back=(FloatingActionButton) findViewById(R.id.fab_back);
    nextother=(FloatingActionButton) findViewById(R.id.fab_ff);
    backother=(FloatingActionButton) findViewById(R.id.fab_rew);
    currentTime_text=(TextView)findViewById(R.id.timecurrent);
    endTime_text=(TextView)findViewById(R.id.Time_end);
    seekBar=(SeekBar)findViewById(R.id.Seekbar);
    avata=(ImageView)findViewById(R.id.avata);

}

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaManager.mediaPlayer.getCurrentPosition();
            finaltime=mediaManager.mediaPlayer.getDuration();

            seekBar.setProgress((int)startTime);
            currentTime_text.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
            ;
            myHandler.postDelayed(this, 100);
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.History) {
            // Handle the camera action
        } else if (id == R.id.Media_list) {
            MediaList();

        } else if (id == R.id.AbumList) {
            mediaManager.getlistMedia();

        } else if (id == R.id.Openforder) {
         //   String Uri=fileManager.
            showFileChooser();

        } else if (id == R.id.Setting) {

        } else if (id == R.id.About) {

        }
        else if (id==R.id.Exit){
            finish();
            System.exit(0);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // Phan danh cho quan ly file
    private static final int FILE_SELECT_CODE = 0;
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void MediaList(){
        RunProgressBar ();
        Intent intent=new Intent(MainActivity.this,ListMedia1.class);

        startActivity(intent);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    Toast.makeText(getBaseContext(),uri.toString()+"",Toast.LENGTH_SHORT).show();
                    try
                    {
                        mediaManager.mediaPlayer.release();
                        mediaManager.creatmediaUri(MainActivity.this,uri);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(),"Error open file",Toast.LENGTH_SHORT).show();
                    }
                    // Get the path
                    String path = null;
                    try {
                        path = fileManager.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void RunProgressBar(){

        progressBar = new ProgressDialog (this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Searching file...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        Thread threa= new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    //     progressBarStatus = downloadFile();

                    try {
                        Intent mIntentToGetListMediaProsse=new Intent (MainActivity.this,GetListMediaProsse.class);
                        //    startActivity (mIntentToGetListMediaProsse);
                        startService (mIntentToGetListMediaProsse);


                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                            progressBarStatus=100;


                        }
                    });
                }

                if (progressBarStatus >= 100) {
                    try {

                        Thread.sleep(2000);
                        Thread.interrupted();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }

        });
        threa.start();

    }

}
