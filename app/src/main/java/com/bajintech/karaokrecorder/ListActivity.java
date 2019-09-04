package com.bajintech.karaokrecorder;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bajintech.karaokrecorder.adapter.FileListAdapter;
import com.bajintech.karaokrecorder.record.FileUtils;

/**
 * Created by mtj on 18/1/22.
 */
public class ListActivity extends Activity {
    ListView listView;
    List<File> list = new ArrayList<File>();
    FileListAdapter adapter;
    String testPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        if("pcm".equals(getIntent().getStringExtra("type"))){
            list=FileUtils.getPcmFiles();
        }else{
            list=FileUtils.getWavFiles();
        }
        
        //添加测试文件
        testPath = "";
        testPath = this.getFilesDir().getAbsolutePath() + "/test44.wav"; // data/data目录
        list.add(new File(testPath));
        
        
        adapter = new FileListAdapter(this, list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ListActivity.this, "position:"+list.get(position).getAbsolutePath()+"=2="+list.get(position).getPath(), Toast.LENGTH_SHORT).show();
				playWavFile(list.get(position).getAbsolutePath());
			}
		});

    }
    
    
    String tempPaht="";
    MediaPlayer mediaPlayer;
    void playWavFile(String path){
    	if(TextUtils.isEmpty(tempPaht)){
    		//没有播放过音乐，直接开始播放
    		//如果没在播放中，立刻开始播放。
    		mediaPlayer = new MediaPlayer();
    		mediaPlayer.reset();
            try {
            	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            	path = path.replaceAll("emulated/0", "emulated/legacy");
            	System.out.println("path:"+path);
				mediaPlayer.setDataSource(path);
				tempPaht = path;
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
//          mediaPlayer.prepareAsync();
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

    	}else{
    		//tempPath 不为空，上一次播放过音乐
    		//先释放资源，然后重播放
    		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                tempPaht = "";
                playWavFile(path);
            }
    	}
    	
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            }
    }
}
