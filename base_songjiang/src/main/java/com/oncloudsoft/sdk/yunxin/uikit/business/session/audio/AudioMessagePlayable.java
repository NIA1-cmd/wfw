package com.oncloudsoft.sdk.yunxin.uikit.business.session.audio;

import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.oncloudsoft.sdk.yunxin.uikit.common.media.audioplayer.Playable;

public class AudioMessagePlayable implements Playable {

    private IMMessage message;

    public IMMessage getMessage() {
        return message;
    }

    public AudioMessagePlayable(IMMessage playableMessage) {
        this.message = playableMessage;
    }

    @Override
    public long getDuration() {
        return ((AudioAttachment) message.getAttachment()).getDuration();
    }

    @Override
    public String getPath() {
        return ((AudioAttachment) message.getAttachment()).getPath();
    }

    @Override
    public boolean isAudioEqual(Playable audio) {
        if (AudioMessagePlayable.class.isInstance(audio)) {
            return message.isTheSame(((AudioMessagePlayable) audio).getMessage());
        } else {
            return false;
        }
    }
}
