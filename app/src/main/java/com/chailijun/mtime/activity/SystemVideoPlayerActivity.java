package com.chailijun.mtime.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chailijun.mtime.R;
import com.chailijun.mtime.customview.VideoView;
import com.chailijun.mtime.mvp.model.MediaItem;
import com.chailijun.mtime.utils.DensityUtil;
import com.chailijun.mtime.utils.Logger;
import com.chailijun.mtime.utils.NetWorkUtil;
import com.chailijun.mtime.utils.TimeUtils;
import com.chailijun.mtime.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import android.widget.VideoView;


public class SystemVideoPlayerActivity extends Activity implements View.OnClickListener {

    private static final int PROGRESS = 1;
    private static final int HIDE_MEDIACONTROLLER = 2;
    private static final int SHOW_SPEED = 3;
    private static final int DEFAULT_SCREEN = 1;
    private static final int FULL_SCREEN = 2;
    /**
     * 自动隐藏控制面板的默认时间
     */
    private static final long HIDE_TIME = 4000;
    private Utils utils;
    /**
     * 监听电量变化的广播
     */
    private BatteryReceiver batteryReceiver;

    private Uri uri;
    private VideoView mVideoView;
    /**
     * 传入进来的视频列表
     */
    private ArrayList<MediaItem> mediaItems;
    /**
     * 要播放的列表中的具体位置
     */
    private int position;

    /**
     * 音量控制
     */
    private AudioManager audioManager;
    /**
     * 当前的音量
     */
    private int currentVoice;

    /**
     * 0~15
     * 最大音量
     */
    private int maxVoice;
    /**
     * 是否是静音
     */
    private boolean isMute;
    /**
     * 是否全屏
     */
    private boolean isFullScreen;
    /**
     * 屏幕的宽
     */
    private int screenWidth = 0;

    /**
     * 屏幕的高
     */
    private int screenHeight = 0;

    /**
     * 真实视频的宽
     */
    private int videoWidth;
    /**
     * 真实视频的高
     */
    private int videoHeight;
    /**
     * 手势识别器
     */
    private GestureDetector detector;
    /**
     * 是否显示控制面板
     */
    private boolean isShowMediaController;
    /**
     * 是否是网络资源
     */
    private boolean isNetUri;
    /**
     * 视频播放时卡顿监听是否采用VideoView封装好的卡顿监听
     */
    private boolean isUseSystem = true;
    /**
     * 视频前一秒播放的位置
     */
    private int preCurrentPosition;
    /**
     * 视频的总时长
     */
    private int duration;

    private TextView tv_brightness;
    private TextView tv_progress;

    private RelativeLayout mediaController;
    private LinearLayout llTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private TextView tv_voice;
    //    private Button btnVoice;
//    private SeekBar seekbarVoice;
    private Button btnSwitchPlayer;
    private LinearLayout llBottom;
    private TextView tvCurrentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private ImageView exit;
    private Button btnVideoPre;
    private Button btnVideoStartPause;
    private Button btnVideoNext;
    private Button btnVideoSwitchScreen;
    private LinearLayout ll_buffer;
    private TextView tv_buffer_netspeed;
    private LinearLayout ll_loading;
    private TextView tv_loading_netspeed;

    private RelativeLayout rl_voice;
    private RelativeLayout rl_brightness;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_SPEED://显示网速
                    //得到网络速度
                    String netSpeed = utils.getNetSpeed(SystemVideoPlayerActivity.this);

                    //显示网络速
                    tv_loading_netspeed.setText("玩命加载中..." + netSpeed);
                    tv_buffer_netspeed.setText("缓存中..." + netSpeed);

                    //2.每两秒更新一次
                    handler.removeMessages(SHOW_SPEED);
                    handler.sendEmptyMessageDelayed(SHOW_SPEED, 2000);

                    break;
                case HIDE_MEDIACONTROLLER://隐藏控制面板
                    hideMediaController();
                    break;
                case PROGRESS:
                    //得到当前的视频播放进度
                    int currentPosition = mVideoView.getCurrentPosition();
                    //seekBar.setProgress(当前进度);
                    seekbarVideo.setProgress(currentPosition);
                    //更新文本播放进度
                    tvCurrentTime.setText(utils.stringForTime(currentPosition));

                    //设置系统时间
                    tvSystemTime.setText(TimeUtils.getSysteTime());

                    //缓存进度的更新
                    if (isNetUri) {
                        //只有网络资源才有缓存效果
                        int bufferPercentage = mVideoView.getBufferPercentage();//0~100
                        int totalBuffer = bufferPercentage * seekbarVideo.getMax();
                        seekbarVideo.setSecondaryProgress(totalBuffer / 100);
                    } else {
                        //本地视频没有缓冲效果
                        seekbarVideo.setSecondaryProgress(0);
                    }

                    //监听卡顿
                    if (!isUseSystem) {
                        if (mVideoView.isPlaying()) {
                            int buffer = currentPosition - preCurrentPosition;
                            if (buffer < 500) {
                                //视频卡了
                                ll_buffer.setVisibility(View.VISIBLE);
                            } else {
                                //视频不卡了
                                ll_buffer.setVisibility(View.GONE);
                            }
                        } else {
                            ll_buffer.setVisibility(View.GONE);
                        }
                    }
                    preCurrentPosition = currentPosition;

                    //每秒更新一次
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    break;
            }
        }
    };

    private void findViews() {
        setContentView(R.layout.activity_system_video_player);
        tv_brightness = (TextView) findViewById(R.id.tv_brightness);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        mVideoView = (VideoView) findViewById(R.id.videoview);
        mediaController = (RelativeLayout) findViewById(R.id.media_controller);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvSystemTime = (TextView) findViewById(R.id.tv_system_time);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
//        btnVoice = (Button) findViewById(R.id.btn_voice);
//        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwitchPlayer = (Button) findViewById(R.id.btn_switch_player);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        seekbarVideo = (SeekBar) findViewById(R.id.seekbar_video);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        exit = (ImageView) findViewById(R.id.btn_exit);
        btnVideoPre = (Button) findViewById(R.id.btn_video_pre);
        btnVideoStartPause = (Button) findViewById(R.id.btn_video_start_pause);
        btnVideoNext = (Button) findViewById(R.id.btn_video_next);
        btnVideoSwitchScreen = (Button) findViewById(R.id.btn_video_siwch_screen);
        ll_buffer = (LinearLayout) findViewById(R.id.ll_buffer);
        tv_buffer_netspeed = (TextView) findViewById(R.id.tv_buffer_netspeed);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        tv_loading_netspeed = (TextView) findViewById(R.id.tv_loading_netspeed);

        rl_voice = (RelativeLayout) findViewById(R.id.rl_voice);
        rl_brightness = (RelativeLayout) findViewById(R.id.rl_brightness);

//        btnVoice.setOnClickListener(this);
        btnSwitchPlayer.setOnClickListener(this);
        exit.setOnClickListener(this);
        btnVideoPre.setOnClickListener(this);
        btnVideoStartPause.setOnClickListener(this);
        btnVideoNext.setOnClickListener(this);
        btnVideoSwitchScreen.setOnClickListener(this);

//        //最大音量和SeekBar关联
//        seekbarVoice.setMax(maxVoice);
//        //设置当前进度-当前音量
//        seekbarVoice.setProgress(currentVoice);

        //开始更新网络速度
        handler.sendEmptyMessage(SHOW_SPEED);
    }

    @Override
    public void onClick(View v) {
//        if (v == btnVoice) {
//            isMute = !isMute;
//            // Handle clicks for btnVoice
//            updataVoice(currentVoice, isMute);
//        } else
        if (v == btnSwitchPlayer) {
            // Handle clicks for btnSwitchPlayer
            showSwitchPlayerDialog();
        } else if (v == exit) {
            // Handle clicks for exit
            finish();
        } else if (v == btnVideoPre) {
            // Handle clicks for btnVideoPre
            playPreVideo();
        } else if (v == btnVideoStartPause) {
            // Handle clicks for btnVideoStartPause
            startAndPause();
        } else if (v == btnVideoNext) {
            // Handle clicks for btnVideoNext
            playNextVideo();
        } else if (v == btnVideoSwitchScreen) {
            // Handle clicks for btnVideoSwitchScreen
            setFullScreenAndDefault();
        }

        //重新发送隐藏控制面板的消息
        handler.removeMessages(HIDE_MEDIACONTROLLER);
        handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
    }

    private void showSwitchPlayerDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("系统播放器提醒您");
        builder.setMessage("当您播放视频，有声音没有画面的时候，请切换万能播放器播放");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startVitamioPlayer();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void setFullScreenAndDefault() {
        if (isFullScreen) {
            //全屏-->默认
            setVideoType(DEFAULT_SCREEN);
        } else {
            //默认-->全屏
            setVideoType(FULL_SCREEN);
        }
    }

    private void setVideoType(int defaultScreen) {
        switch (defaultScreen) {
            case FULL_SCREEN:
                //设置视频为全屏显示
                mVideoView.setVideoSize(screenWidth, screenHeight);
                //设置按钮的状态-->默认
                btnVideoSwitchScreen.setBackgroundResource(R.drawable.btn_video_switch_screen_default_selector);
                isFullScreen = true;

//                //全屏时隐藏控制面板
//                hideMediaController();
                break;
            case DEFAULT_SCREEN:
                //设置视频画面为默认大小

                //视频真实的宽和高
                int mVideoWidth = videoWidth;
                int mVideoHeight = videoHeight;

                //屏幕的宽和高
                int width = screenWidth;
                int height = screenHeight;

                // for compatibility, we adjust size based on aspect ratio
                if (mVideoWidth * height < width * mVideoHeight) {
                    width = height * mVideoWidth / mVideoHeight;
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    height = width * mVideoHeight / mVideoWidth;
                }

                mVideoView.setVideoSize(width, height);
                //设置按钮的状态-->全屏
                btnVideoSwitchScreen.setBackgroundResource(R.drawable.btn_video_siwch_screen_full_selector);
                isFullScreen = false;
                break;
        }
    }

    private void startAndPause() {
        if (mVideoView.isPlaying()) {
            //视频在播放-设置暂停
            mVideoView.pause();
            //按钮状态设置播放
            btnVideoStartPause.setBackgroundResource(R.drawable.video_play);
        } else {
            //视频播放
            mVideoView.start();
            //按钮状态设置暂停
            btnVideoStartPause.setBackgroundResource(R.drawable.video_pause);
        }
    }

    /**
     * 播放上一个视频
     */
    private void playPreVideo() {
        if (mediaItems != null && mediaItems.size() > 0) {
            //播放上一个视频
            position--;
            if (position >= 0) {
                ll_loading.setVisibility(View.VISIBLE);
                MediaItem mediaItem = mediaItems.get(position);
                tvName.setText(mediaItem.getName());
                isNetUri = utils.isNetUri(selectUrl(mediaItem));
                mVideoView.setVideoPath(selectUrl(mediaItem));

                //设置按钮状态
                setButtonState();
            }
        } else if (uri != null) {
            //设置按钮状态-上一个和下一个按钮设置灰色并且不可以点击
            setButtonState();
        }
    }

    /**
     * 播放下一个视频
     */
    private void playNextVideo() {
        if (mediaItems != null && mediaItems.size() > 0) {
            //播放下一个
            position++;
            if (position < mediaItems.size()) {

                ll_loading.setVisibility(View.VISIBLE);
                MediaItem mediaItem = mediaItems.get(position);
                tvName.setText(mediaItem.getName());
                isNetUri = utils.isNetUri(selectUrl(mediaItem));
                mVideoView.setVideoPath(selectUrl(mediaItem));

                //设置按钮状态
                setButtonState();
            }
        } else if (uri != null) {
            //设置按钮状态-上一个和下一个按钮设置灰色并且不可以点击
            setButtonState();
        }
    }

    private void setButtonState() {
        if (mediaItems != null && mediaItems.size() > 0) {
            if (mediaItems.size() == 1) {
                setEnable(false);
            } else if (mediaItems.size() == 2) {
                if (position == 0) {
                    btnVideoPre.setBackgroundResource(R.drawable.video_backward_disable);
                    btnVideoPre.setEnabled(false);

                    btnVideoNext.setBackgroundResource(R.drawable.video_forward);
                    btnVideoNext.setEnabled(true);

                } else if (position == mediaItems.size() - 1) {
                    btnVideoNext.setBackgroundResource(R.drawable.video_forward_disable);
                    btnVideoNext.setEnabled(false);

                    btnVideoPre.setBackgroundResource(R.drawable.video_backward);
                    btnVideoPre.setEnabled(true);

                }
            } else {
                if (position == 0) {
                    btnVideoPre.setBackgroundResource(R.drawable.video_backward_disable);
                    btnVideoPre.setEnabled(false);
                } else if (position == mediaItems.size() - 1) {
                    btnVideoNext.setBackgroundResource(R.drawable.video_forward_disable);
                    btnVideoNext.setEnabled(false);
                } else {
                    setEnable(true);
                }
            }
        } else if (uri != null) {
            //两个按钮设置灰色
            setEnable(false);
        }
    }

    private void setEnable(boolean isEnable) {
        if (isEnable) {
            btnVideoPre.setBackgroundResource(R.drawable.video_backward);
            btnVideoPre.setEnabled(true);
            btnVideoNext.setBackgroundResource(R.drawable.video_forward);
            btnVideoNext.setEnabled(true);
        } else {
            //两个按钮设置灰色
            btnVideoPre.setBackgroundResource(R.drawable.video_backward_disable);
            btnVideoPre.setEnabled(false);
            btnVideoNext.setBackgroundResource(R.drawable.video_forward_disable);
            btnVideoNext.setEnabled(false);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        findViews();

        setListener();

        getData();

        setData();
    }

    @Override
    protected void onDestroy() {
        //释放资源的时候，先释放子类，在释放父类

        //移除所有的消息
        handler.removeCallbacksAndMessages(null);
        //解注册广播接收器
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }
        super.onDestroy();
    }

    private void initData() {
        utils = new Utils();

        //注册电量广播
        batteryReceiver = new BatteryReceiver();
        IntentFilter intentFiler = new IntentFilter();
        //当电量变化的时候发这个广播
        intentFiler.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, intentFiler);

        //实例化手势识别器，并且重写双击，单击，长按
        detector = new GestureDetector(this, new MySimpleOnGestureListener());

        //得到屏幕的宽和高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //得到音量
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        currentVoice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVoice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * 长按
         *
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
//            Toast.makeText(SystemVideoPlayerActivity.this, "长按", Toast.LENGTH_SHORT).show();
            startAndPause();
        }

        /**
         * 双击
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            setFullScreenAndDefault();
            return super.onDoubleTap(e);
        }

        /**
         * 单击
         *
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isShowMediaController) {
                //隐藏
                hideMediaController();
                //把隐藏消息移除
                handler.removeMessages(HIDE_MEDIACONTROLLER);

            } else {
                //显示
                showMediaController();
                //发消息隐藏
                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    /**
     * 显示控制面板
     */
    private void showMediaController() {
        mediaController.setVisibility(View.VISIBLE);
        isShowMediaController = true;
    }


    /**
     * 隐藏控制面板
     */
    private void hideMediaController() {
        mediaController.setVisibility(View.GONE);
        isShowMediaController = false;
    }

    private void setListener() {
        //准备好时的监听
        mVideoView.setOnPreparedListener(new VideoOnPreparedListener());
        //播放出错时的监听
        mVideoView.setOnErrorListener(new VideoOnErrorListener());
        //播放完成了时的监听
        mVideoView.setOnCompletionListener(new VideoOnCompletionListener());

        //设置SeeKbar状态变化的监听
        seekbarVideo.setOnSeekBarChangeListener(new VideoOnSeekBarChangeListener());

//        seekbarVoice.setOnSeekBarChangeListener(new VoiceOnSeekBarChangeListener());

        if (isUseSystem) {
            //设置视频播放卡顿监听
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mVideoView.setOnInfoListener(new MyOnInfoListener());
            }
        }
    }

    class MyOnInfoListener implements MediaPlayer.OnInfoListener {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START://视频卡顿、拖动卡顿
                    ll_buffer.setVisibility(View.VISIBLE);
                    break;

                case MediaPlayer.MEDIA_INFO_BUFFERING_END://卡顿结束
                    ll_buffer.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }

    private void getData() {
        //得到播放地址
        uri = getIntent().getData();
        mediaItems = (ArrayList<MediaItem>) getIntent().getSerializableExtra("videolist");
        position = getIntent().getIntExtra("position", 0);
    }

    private void setData() {
        if (mediaItems != null && mediaItems.size() > 0) {
            MediaItem mediaItem = mediaItems.get(position);
            tvName.setText(mediaItem.getName());//设置视频的名称
            isNetUri = utils.isNetUri(selectUrl(mediaItem));
            mVideoView.setVideoPath(selectUrl(mediaItem));
        } else if (uri != null) {
            tvName.setText(uri.toString());//设置视频的名称
            isNetUri = utils.isNetUri(uri.toString());
            mVideoView.setVideoURI(uri);
        } else {
            Toast.makeText(SystemVideoPlayerActivity.this, "当前未传入任何视频数据", Toast.LENGTH_SHORT).show();
        }
        setButtonState();
    }

    class VideoOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
//            Toast.makeText(SystemVideoPlayerActivity.this, "准备好了", Toast.LENGTH_SHORT).show();
            videoWidth = mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            mVideoView.start();
            //视频的总时长关联seekBar总长度
            duration = mVideoView.getDuration();
            seekbarVideo.setMax(duration);
            tvDuration.setText(utils.stringForTime(duration));

            hideMediaController();//默认是隐藏控制面板

            //发消息更新seekbarVideo进度
            handler.sendEmptyMessage(PROGRESS);

            //屏幕的默认播放
            setVideoType(DEFAULT_SCREEN);

            //把加载页面消失掉
            ll_loading.setVisibility(View.GONE);

        }
    }

    class VideoOnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
//            Toast.makeText(SystemVideoPlayerActivity.this, "播放出错", Toast.LENGTH_SHORT).show();
            //1.播放的视频格式不支持--跳转到万能播放器继续播放
            startVitamioPlayer();
            Logger.e("TAG", "系统播放器播放出错");
            //2.播放网络视频的时候，网络中断---1.如果网络确实断了，可以提示用于网络断了；2.网络断断续续的，重新播放
            //3.播放的时候本地文件中间有空白---下载做完成
            return true;
        }
    }

    /**
     * a.把数据按照原样传入VtaimoVideoPlayer播放器
     * b.关闭系统播放器
     */
    private void startVitamioPlayer() {

//        if (mVideoView != null) {
//            mVideoView.stopPlayback();
//        }
//
//        Intent intent = new Intent(this, VitamioVideoPlayerActivity.class);
//        if (mediaItems != null && mediaItems.size() > 0) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("videolist", mediaItems);
//            intent.putExtras(bundle);
//            intent.putExtra("position", position);
//        } else if (uri != null) {
//            intent.setUrl(uri);
//        }
//        startActivity(intent);
//
//        finish();//关闭页面
    }

    class VideoOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
//            Toast.makeText(SystemVideoPlayerActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            playNextVideo();
        }
    }

    class VideoOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        /**
         * 手指滑动时的回调
         *
         * @param seekBar
         * @param progress
         * @param fromUser 若为用户引起的滑动，则此参数为true,否则为false
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mVideoView.seekTo(progress);
            }
        }

        /**
         * 当手指触碰时的回调
         *
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_MEDIACONTROLLER);
        }

        /**
         * 当手指离开时的回调
         *
         * @param seekBar
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
        }
    }

    class VoiceOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
//                if (progress > 0) {
//                    isMute = false;
//                } else {
//                    isMute = true;
//                }
                isMute = progress > 0 ? false : true;
                updataVoice(progress, isMute);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(HIDE_MEDIACONTROLLER);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
        }
    }

    /**
     * 设置音量的大小
     *
     * @param progress
     */
    private void updataVoice(int progress, boolean isMute) {
        Logger.e("TAG", "updataVoice--->调节音量");
        if (isMute) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//            seekbarVoice.setProgress(0);
            tv_voice.setText("0%");
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
//            seekbarVoice.setProgress(progress);
            int percent = (int) (progress / 15.0 * 100);
            //显示音量百分比的布局
            rl_voice.setVisibility(View.VISIBLE);
            tv_voice.setText(percent + "%");
            currentVoice = progress;
        }
    }

    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);//0~100;
            setBattery(level);//主线程
        }
    }

    private void setBattery(int level) {
        if (level <= 0) {
            ivBattery.setImageResource(R.drawable.ic_battery_000);
        } else if (level <= 10) {
            ivBattery.setImageResource(R.drawable.ic_battery_010);
        } else if (level <= 20) {
            ivBattery.setImageResource(R.drawable.ic_battery_020);
        } else if (level <= 40) {
            ivBattery.setImageResource(R.drawable.ic_battery_040);
        } else if (level <= 60) {
            ivBattery.setImageResource(R.drawable.ic_battery_060);
        } else if (level <= 80) {
            ivBattery.setImageResource(R.drawable.ic_battery_080);
        } else if (level <= 100) {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        } else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    private float startY;
    private float startX;

    private float touchRangMin;
    private float touchRangMax;

    /**
     * 当一按下的音量
     */
    private int mVol;

    private boolean FLAG_BRIGHTNESS;
    private boolean FLAG_VOICE;
    private boolean FLAG_PROGRESS;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startX = event.getX();
                mVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                touchRangMin = Math.min(screenHeight, screenWidth);//screenHeight
                touchRangMax = Math.max(screenHeight, screenWidth);//screenHeight

                handler.removeMessages(HIDE_MEDIACONTROLLER);
                break;
            case MotionEvent.ACTION_MOVE:
                //2.移动的记录相关值
                float endY = event.getY();
                float endX = event.getX();
                float distanceX = startX - endX;
                float distanceY = startY - endY;

                final double FLING_MIN_DISTANCE = 0.5;
                final double FLING_MIN_VELOCITY = 0.5;
                if (Math.abs(distanceY) > Math.abs(distanceX)
                        && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                    if (endX < screenWidth / 2) {
                        //左边屏幕-调节亮度
                        if (!FLAG_BRIGHTNESS){
                            FLAG_PROGRESS = true;
                            FLAG_VOICE = true;
                            setBrightness(distanceY > 0 ? 5 : -5);
                        }
                    } else {
                        //右边屏幕-调节声音
                        //改变声音 = （滑动屏幕的距离： 总距离）*音量最大值
                        float delta = (distanceY / touchRangMin) * maxVoice;
                        //最终声音 = 原来的 + 改变声音；
                        int voice = (int) Math.min(Math.max(mVol + delta, 0), maxVoice);
                        if (delta != 0) {
                            isMute = false;
                            if (!FLAG_VOICE){
                                FLAG_BRIGHTNESS = true;
                                FLAG_PROGRESS = true;
                                updataVoice(voice, isMute);
                            }
                        }
                    }
                } else {
                    mVideoView.pause();
                    if (!FLAG_PROGRESS){
                        FLAG_VOICE = true;
                        FLAG_BRIGHTNESS = true;
                        adjustVideoProgress(distanceX);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                FLAG_BRIGHTNESS = false;
                FLAG_VOICE = false;
                FLAG_PROGRESS = false;

                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
                rl_voice.setVisibility(View.GONE);
                rl_brightness.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);

                handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 滑动屏幕改变视频播放进度（快进、快退）
     *
     * @param distanceX
     */
    private void adjustVideoProgress(float distanceX) {
        Logger.e("TAG", "adjustVideoProgress--->调节进度");
        float STEP_PROGRESS = 5;
        int playingTime = mVideoView.getCurrentPosition();
        if (distanceX >= DensityUtil.dp2px(STEP_PROGRESS)) {// 快退，用步长控制改变速度，可微调
            if (playingTime > 3) {// 避免为负
                playingTime -= 3000;// scroll方法执行一次快退3秒
            } else {
                playingTime = 0;
            }
        } else if (distanceX <= -DensityUtil.dp2px(STEP_PROGRESS)) {// 快进
            if (playingTime < duration - 6000) {// 避免超过总时长
                playingTime += 3000;// scroll执行一次快进3秒
            } else {
                playingTime = duration - 1000;
            }
        }
        if (playingTime < 0) {
            playingTime = 0;
        }
        mVideoView.seekTo(playingTime);
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText(utils.stringForTime(playingTime) + "/" + utils.stringForTime(duration));
    }

    /*
     *
     * 设置屏幕亮度 lp = 0 全暗 ，lp= -1,根据系统设置， lp = 1; 最亮
     */
    public void setBrightness(float brightness) {
        Logger.e("TAG", "setBrightness--->调节亮度");
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = lp.screenBrightness + brightness / 255.0f;
        if (lp.screenBrightness > 1) {
            lp.screenBrightness = 1;
        } else if (lp.screenBrightness < 0) {
            lp.screenBrightness = 0;
        }
        getWindow().setAttributes(lp);
        //在页面显示当前屏幕的亮度值
        float currScreenBrightness = getWindow().getAttributes().screenBrightness;
        //显示亮度百分比的布局
        rl_brightness.setVisibility(View.VISIBLE);
        tv_brightness.setText((int) (currScreenBrightness * 100) + "%");
    }

    /**
     * 监听物理健，实现声音的调节大小
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            currentVoice--;
            updataVoice(currentVoice, false);
            handler.removeMessages(HIDE_MEDIACONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
            return true;//若返回false则系统的音量面板同时也会出现
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            currentVoice++;
            updataVoice(currentVoice, false);
            handler.removeMessages(HIDE_MEDIACONTROLLER);
            handler.sendEmptyMessageDelayed(HIDE_MEDIACONTROLLER, HIDE_TIME);
            return true;//若返回false则系统的音量面板同时也会出现
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 根据不同的网络连接状态选择不同格式的视频
     * @param mediaItem
     * @return
     */
    private String selectUrl(MediaItem mediaItem){

        String url = null;

        if (NetWorkUtil.isNetWorkAvailable(getApplicationContext())){
            if (NetWorkUtil.is3gConnected(getApplicationContext())){
                url = mediaItem.getUrl();
            }else if(NetWorkUtil.isWifiConnected(getApplicationContext())){
                url = mediaItem.getHightUrl();
            }
        }else {
            NetWorkUtil.setNetworkMethod(this);
        }

        return url;
    }
}