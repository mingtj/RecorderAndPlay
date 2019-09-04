package com.bajintech.karaokrecorder.callback;

/**
 * Created by mtj on 18/1/22.
 * 获取录音的音频流,用于拓展的处理
 */
public interface RecordStreamListener {
    void recordOfByte(byte[] data, int begin, int end);
}
