package com.oncloudsoft.sdk.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.oncloudsoft.sdk.app.Global;
import com.oncloudsoft.sdk.event.RecordVolumeEvent;
import com.oncloudsoft.sdk.utils.FileUtil;
import com.oncloudsoft.sdk.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import static android.os.Build.VERSION_CODES.BASE;

/**
 * 录音的 Service
 * <p>
 * Created by developerHaoz on 2017/8/12.
 */

public class RecordingService extends Service {


    private static final String LOG_TAG = "RecordingService";

//    private String mFileName = null;
//    private String mFilePath = null;

    private MediaRecorder mRecorder = null;

    //    private long mStartingTimeMillis = 0;
//    private long mElapsedMillis = 0;
    private TimerTask mIncrementTimerTask = null;


    Handler handler=new Handler();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording(intent.getStringExtra(Global.PATH));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null) {
            stopRecording();
        }
        super.onDestroy();
    }

    public void startRecording(String path) {
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(file.getPath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(192000);

        try {
            mRecorder.prepare();
            mRecorder.start();
            updateMicStatus();


//            mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void updateMicStatus() {

        if (mRecorder != null) {
            int ratio = mRecorder.getMaxAmplitude() / BASE;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10(ratio));
            RecordVolumeEvent recordVolumeEvent=new RecordVolumeEvent(db / 2);
            EventBus.getDefault().post(recordVolumeEvent);
            MyLog.d(db / 2+"");
            handler.postDelayed(mUpdateMicStatusTimer, 1000);
        }


    }




    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };


    public String getFileNameAndPath() {

        String path = FileUtil.getAuditTempt();

        return FileUtil.creatFile(path).getPath();//创建录音临时文件

    }

    public void stopRecording() {
        if (mRecorder != null) {
            mRecorder.setOnErrorListener(null);
            mRecorder.setOnInfoListener(null);
            mRecorder.setPreviewDisplay(null);
            mRecorder.stop();
            mRecorder.release();
            handler.removeCallbacks(mUpdateMicStatusTimer);
            mRecorder = null;

        }


        if (mIncrementTimerTask != null) {
            mIncrementTimerTask.cancel();
            mIncrementTimerTask = null;
        }

    }

}
