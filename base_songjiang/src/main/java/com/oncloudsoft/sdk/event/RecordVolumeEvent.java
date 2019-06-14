package com.oncloudsoft.sdk.event;

/**
 * 作者 黄继栋
 * 创建时间 2019/3/22 9:54
 * 描述 录音是音量大小的事件
 */

public class RecordVolumeEvent {
    private int volume;

    public RecordVolumeEvent(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
