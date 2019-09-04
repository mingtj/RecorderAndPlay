package com.bajintech.karaokrecorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bajintech.karaokrecorder.record.AudioRecorder;
import com.bajintech.karaokrecorder.utils.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private static final String TAG = "MainActivity";
	private int REQUESSION_AUDIO_RECORD_PERMISSION = 1;
	private Button btnStartRecord,btnPauseRecord,btnStopRecord,btnPcmList,btnWavList;
	private Button btnPlay,btnPausePlay,btnStopPlay;
	AudioRecorder audioRecorder;
	String testAudioPath;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		if(Utils.audioFileIsExist(this)){
//			System.out.println("audio is exist...");
//		}else{
//			Utils.copyMvtoData(this);
//		}
		initView();
	}

	void initView(){
		btnStartRecord = (Button) findViewById(R.id.start);
		btnStartRecord.setOnClickListener(this);
		
		btnPauseRecord = (Button) findViewById(R.id.pause);
		btnPauseRecord.setOnClickListener(this);
		
		btnStopRecord = (Button) findViewById(R.id.stop);
		btnStopRecord.setOnClickListener(this);
		
		btnPlay = (Button) findViewById(R.id.play);
		btnPlay.setOnClickListener(this);
		
		btnPcmList = (Button) findViewById(R.id.pcmList);
		btnPcmList.setOnClickListener(this);
	
		btnWavList = (Button) findViewById(R.id.wavList);
		btnWavList.setOnClickListener(this);
		
		btnPauseRecord.setVisibility(View.GONE);
		audioRecorder = AudioRecorder.getInstance();
		
		btnPausePlay = (Button) findViewById(R.id.pause_audio);
		btnPausePlay.setOnClickListener(this);
		
		btnStopPlay = (Button) findViewById(R.id.stop_audio);
		btnStopPlay.setOnClickListener(this);
		
		testAudioPath = this.getFilesDir().getAbsolutePath() + "/test44.wav"; // data/data目录

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
//			if (ContextCompat.checkSelfPermission(this,
//	                Manifest.permission.RECORD_AUDIO)
//	                != PackageManager.PERMISSION_GRANTED)
//	        {
//
//	            ActivityCompat.requestPermissions(this,
//	                    new String[]{Manifest.permission.RECORD_AUDIO},
//	                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
//	        } else
//	        {
			
			
//	        }
			
//			//⑧申请录制音频的动态权限//
//            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
//                    != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this,new String[]{
//                    android.Manifest.permission.RECORD_AUDIO},REQUESSION_AUDIO_RECORD_PERMISSION);
//            }else {
//            	//开始录音
////                startRecord();
//            	
//            }
			
			try {
                if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_NO_READY) {
                    //初始化录音
                    String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    audioRecorder.createDefaultAudio(fileName);
                    audioRecorder.startRecord(null);

                    btnStartRecord.setText("停止录音");

                    btnPauseRecord.setVisibility(View.VISIBLE);

                } else {
                    //停止录音
                    audioRecorder.stopRecord();
                    btnStartRecord.setText("开始录音");
                    btnPauseRecord.setText("暂停录音");
                    btnPauseRecord.setVisibility(View.GONE);
                }

            } catch (IllegalStateException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.pause:
			try {
                if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
                    //暂停录音
                    audioRecorder.pauseRecord();
                    btnPauseRecord.setText("继续录音");
                    break;

                } else {
                    audioRecorder.startRecord(null);
                    btnPauseRecord.setText("暂停录音");
                }
            } catch (IllegalStateException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
			break;
		case R.id.stop:
//			status = Status.AUDIO_STOP;
//            resetButton();
			try {
				audioRecorder.stopRecord();
				btnStartRecord.setText("start");
				btnPauseRecord.setText("pause");
				btnPauseRecord.setVisibility(View.GONE);
				
			 } catch (IllegalStateException e) {
	            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
	         }
			break;
		case R.id.play:
			if(!Utils.audioFileIsExist(this)){
                Toast.makeText(this,"文件拷贝中，请稍后....",Toast.LENGTH_SHORT).show();
                return;
            }else{
            	// /data/data/com.bajintech.karaokrecorder/files/test44.wav
            	playWavFile(testAudioPath);
            }
		case R.id.pause_audio:
			if(mediaPlayer!=null){ 
				if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
						btnPausePlay.setText("continue play");
//						System.out.println("==play state:"+mediaPlayer.);
				}else{
					mediaPlayer.start();
					btnPausePlay.setText("pauseAudio");
				}
			}else{
				System.out.println("==mediaplay is null===");
			}
			break;
		case R.id.stop_audio:
			if(mediaPlayer!=null){
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer = null;
			}
			break;
		case R.id.pcmList:
            Intent showPcmList = new Intent(MainActivity.this, ListActivity.class);
            showPcmList.putExtra("type", "pcm");
            startActivity(showPcmList);
            break;

        case R.id.wavList:
            Intent showWavList = new Intent(MainActivity.this, ListActivity.class);
            showWavList.putExtra("type", "wav");
            startActivity(showWavList);
            break;
            
		default:
			break;
		}
	}
	
	
	/**
     * ⑨重写onRequestPermissionsResult方法
     * 获取动态权限请求的结果,再开启录制音频
     */
	public void onRequestPermissionsResult(int requestCode,
	        String permissions[], int[] grantResults) {
	    if(requestCode==REQUESSION_AUDIO_RECORD_PERMISSION) {
	            // If request is cancelled, the result arrays are empty.
	            if (grantResults.length > 0
	                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

	                // permission was granted, yay! Do the
	                // contacts-related task you need to do.
	            	System.out.println("===has grant record permission===");
	            	Toast.makeText(this,"权限成功",Toast.LENGTH_SHORT).show();
	            } else {

	                // permission denied, boo! Disable the
	                // functionality that depends on this permission.
	            	System.out.println("===has not grant record permission===");
	            	Toast.makeText(this,"用户拒绝了权限",Toast.LENGTH_SHORT).show();
	            }
	    }
	    
//	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	
//    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
//        if(requestCode==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
////            startRecord();
//        	
//        }else {
//            Toast.makeText(this,"用户拒绝了权限",Toast.LENGTH_SHORT).show();
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    
    
    //
//    void requestRecordPermission(){
//    	 Log.d(TAG, "requestRecordPermission");//Success get user permissions.
//
//         IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//         if(null==context){
//         	System.out.println("==context=null=");
//         }else{2
//         	System.out.println("==context=exist=");
//         }
//         context.registerReceiver(mUsbReceiver, filter);
//         PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
//         mUsbManager.requestPermission(mUsbDevice, mPermissionIntent);
//    }
//	
	MediaPlayer mediaPlayer;
    void playWavFile(String path){
    	System.out.println("audio play path:"+path);
    	if(mediaPlayer!=null && mediaPlayer.isPlaying()){
    		//先释放资源，然后重播放
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                playWavFile(path);
    	}else{
    		//没有在播放歌曲
    		//没有播放过音乐，直接开始播放
    		//如果没在播放中，立刻开始播放。
//    		destoryMediaPlayer();
    		mediaPlayer = new MediaPlayer();
    		mediaPlayer.reset();
    		
            try {
            	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            	System.out.println("path:"+path);
				mediaPlayer.setDataSource(path);
				
				mediaPlayer.prepare();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // 通过异步的方式装载媒体资源
//            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {                    
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });
            
         // 设置循环播放
          mediaPlayer.setLooping(true);
          mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
              @Override
              public void onCompletion(MediaPlayer mp) {
                  // 在播放完毕被回调
            	  mediaPlayer.reset();
              }
          });
    		
    	}
    	
    }

//    public void destoryMediaPlayer() {
//        try {
//            if (mediaPlayer != null) {
//                mediaPlayer.setOnCompletionListener(null);
//                mediaPlayer.setOnPreparedListener(null);
//                mediaPlayer.reset();
//                mediaPlayer.release();
//                mediaPlayer = null;
//            }
//        } catch (Exception e) {
//
//        }
//    }
    
	@Override
    protected void onPause() {
        super.onPause();
        if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
            audioRecorder.pauseRecord();
            btnPauseRecord.setText("继续录音");
        }

    }

    @Override
    protected void onDestroy() {
        audioRecorder.release();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            }
        super.onDestroy();

    }
    
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
