package com.example.yucong.musicdemo;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    private int playPosition;
    private ListView mListView;
    private List<MusicInfor> mylist;
    private MyAdapter adapter;
    private static MediaPlayer mediaPlayer;
    private ImageButton btn_playPause;
    private ImageButton btn_next;
    private ImageButton btn_previous;
    private ImageButton btn_model;
    private ImageButton igv_tupian;
    private Switch aSwitch;
    private SeekBar seekBar;
    private static TextView tv_time_now;
    private static TextView tv_time_all;
    private static TextView tv_music_name;
    private Timer timer;
    private int currentPosition;
    private int duration;
    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突
    private boolean IsPlay = false;//是否有歌曲在播放
    private SensorManager sensorManager;
    private Vibrator vibrator = null;
    int playmodel = 1;
    int switchModel = 1;
    int i = 0;
    public static String song;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION"
    };


    public static void verifyStoragePermissions(Activity activity) {

        try {

            int permission3=ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");


            if(permission3!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            };


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        initView();

        mediaPlayer = new MediaPlayer();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                play(mylist.get(i).path);
                playPosition = i;
                IsPlay = true;
                btn_playPause.setBackgroundResource(R.mipmap.pause);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playmodel == 1) {
                    if (playPosition >= mylist.size() - 1) {
                        playPosition = 0;
                    } else {
                        playPosition++;
                    }
                } else if (playmodel == 2) {
                    if (playPosition >= mylist.size() - 1) {
                        playPosition = 0;
                    } else {
                        Random random = new Random();
                        int min = 0;
                        int max = mylist.size();
                        playPosition = random.nextInt(max) % (max - min + 1) + min;
                    }
                }
                play(mylist.get(playPosition).path);
                btn_playPause.setBackgroundResource(R.mipmap.pause);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d("tad", "播放完毕");
                MusicInfor musicInfor = mylist.get(++playPosition);
                play(musicInfor.path);
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playPosition <= 0) {
                    Toast.makeText(MainActivity.this, "已经是第一首了", Toast.LENGTH_SHORT).show();
                } else {
                    playPosition--;
                    play(mylist.get(playPosition).path);
                    btn_playPause.setBackgroundResource(R.mipmap.pause);
                }
            }
        });
        btn_playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsPlay == false) {
                    btn_playPause.setBackgroundResource(R.mipmap.pause);
                    mediaPlayer.start();
                    IsPlay = true;
                } else {
                    btn_playPause.setBackgroundResource(R.mipmap.play);
                    mediaPlayer.pause();
                    IsPlay = false;
                }
            }
        });
        btn_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 == 1) {
                    playmodel = 2;
                    btn_model.setBackgroundResource(R.mipmap.model_suiji);
                } else {
                    playmodel = 1;
                    btn_model.setBackgroundResource(R.mipmap.model_shunxu);
                }
            }
        });
        igv_tupian = (ImageButton) findViewById(R.id.igv_tupian);
        igv_tupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LyricActivity.class);
                startActivity(intent);
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());
        tv_music_name = (TextView) findViewById(R.id.tv_music_info);
        tv_time_now = (TextView) findViewById(R.id.tv_time_now);
        tv_time_all = (TextView) findViewById(R.id.tv_time_all);
        btn_model = (ImageButton) findViewById(R.id.btn_model);
        aSwitch = (Switch)findViewById(R.id.btn_switch);

        aSwitch.setChecked(false);
        aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.switch_off);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.switch_on);
                    switchModel = 2;
                }else {
                    aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.switch_off);
                    switchModel = 1;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void play(String path){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync(); //异步准备音频资源,缓冲
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){ //当装载流媒体完毕的时候回调
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.seekTo(currentPosition);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isSeekBarChanging){
                                if (mediaPlayer.isPlaying()) {
                                    seekBar.setMax(mediaPlayer.getDuration());
                                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                    Message message = new Message();
                                    handler.sendMessage(message);
                                }
                            }
                        }
                    },0,200);
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {
        //当进度改变后用于通知客户端的回调函数

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            mediaPlayer.seekTo(seekBar.getProgress());
        }
    }
    public  Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (!mediaPlayer.isPlaying())
                return;
            int duration = mediaPlayer.getDuration();
            int currentPosition = mediaPlayer.getCurrentPosition();
            String name = mylist.get(playPosition).song;
            song = name;
            tv_music_name.setText("正在播放:"+ name);
            int minute = duration/1000/60;
            int second = duration/1000%60;

            String strMinute = null;
            String strSecond = null;
            if(minute < 10) {
                strMinute = "0" + minute;
            } else {
                strMinute = minute + "";
            }
            if(second < 10)
            {
                strSecond = "0" + second;
            } else {
                strSecond = second + "";
            }

            tv_time_all.setText(strMinute + ":" + strSecond);

            //歌曲当前播放时长
            minute = currentPosition / 1000 / 60;
            second = currentPosition / 1000 % 60;

            if(minute < 10) {
                strMinute = "0" + minute;
            } else {
                strMinute = minute + "";
            }
            if(second < 10) {
                strSecond = "0" + second;
            } else {
                strSecond = second + "";
            }
            tv_time_now.setText(strMinute + ":" + strSecond);
        }
    };

    private void initView(){
        btn_next = (ImageButton)findViewById(R.id.btn_next);
        btn_previous = (ImageButton)findViewById(R.id.btn_previous);
        btn_playPause = (ImageButton)findViewById(R.id.btn_play_pause);
        btn_model = (ImageButton)findViewById(R.id.btn_model);
        mListView = (ListView)findViewById(R.id.music_list_view);
        tv_music_name = (TextView)findViewById(R.id.tv_music_info);
        mylist = new ArrayList<>();
        mylist = getMusicData(this);
        adapter = new MyAdapter(this,mylist);
        mListView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        if (sensorManager != null){
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float xValue = sensorEvent.values[0];
            float yValue = sensorEvent.values[1];
            float zValue = sensorEvent.values[2];
            if ((xValue > 13) && switchModel==2){
                Toast.makeText(MainActivity.this,"摇一摇：上一首",Toast.LENGTH_SHORT).show();
                if (!mediaPlayer.isPlaying())
                    return;
                if (playPosition >= mylist.size()-1){
                    playPosition = 0;
                }else {
                    playPosition--;
                }
                play(mylist.get(playPosition).path);
                vibrator.vibrate(500);
            }else if ((xValue < -13) && switchModel==2){
                Toast.makeText(MainActivity.this,"摇一摇：下一首",Toast.LENGTH_SHORT).show();
                if (!mediaPlayer.isPlaying())
                    return;
                if (playmodel == 1){
                    if (playPosition >= mylist.size()-1){
                        playPosition = 0;
                    }else {
                        playPosition++;
                    }
                }else if (playmodel == 2) {
                    if (playPosition >= mylist.size() - 1) {
                        playPosition = 0;
                    } else {
                        Random random = new Random();
                        int min = 0;
                        int max = mylist.size();
                        playPosition = random.nextInt(max) % (max - min + 1) + min;
                    }
                }
                play(mylist.get(playPosition).path);
                vibrator.vibrate(500);
            }else if((zValue > 13) && switchModel == 2) {
                Toast.makeText(MainActivity.this,"摇一摇：暂停",Toast.LENGTH_SHORT).show();
                if (!mediaPlayer.isPlaying()){
                    return;
                }else if (mediaPlayer.isPlaying()){
                    btn_playPause.setBackgroundResource(R.mipmap.play);
                    mediaPlayer.pause();
                    IsPlay = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Intent intent=new Intent(MainActivity.this,paintactivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shake) {

        } else if (id == R.id.nav_timing) {
            Intent intent=new Intent(MainActivity.this,TimeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static ArrayList<MusicInfor> getMusicData(Context context){
        ArrayList<MusicInfor> mylist = new ArrayList<>();
        /*Cursor cursor = resolver.query(_uri, prjs, selections, selectArgs, order);
          Uri：这个Uri代表要查询的数据库名称加上表的名称。
          Prjs：这个参数代表要从表中选择的列，用一个String数组来表示。
          Selections：相当于SQL语句中的where子句，就是代表你的查询条件。
          selectArgs：这个参数是说你的Selections里有？这个符号是，这里可以以实际值代替这个问号。如果Selections这个没有？的话，那么这个String数组可以为null。
          Order：说明查询结果按什么来排序。*/
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,
                MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null){
            while (cursor.moveToNext()){
                MusicInfor musicInfor = new MusicInfor();
                musicInfor.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                musicInfor.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                musicInfor.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                musicInfor.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                musicInfor.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                musicInfor.url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                if (musicInfor.size>1024*800){
                    if (musicInfor.song.contains("-")) {
                        String[] str = musicInfor.song.split("-");
                        musicInfor.singer = str[0];
                        musicInfor.song = str[1];
                    }
                    mylist.add(musicInfor);
                }
            }
            cursor.close();
        }
        return mylist;
    }
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }
    }

}
