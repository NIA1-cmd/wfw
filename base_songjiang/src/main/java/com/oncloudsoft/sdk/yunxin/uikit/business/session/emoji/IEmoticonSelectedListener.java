package com.oncloudsoft.sdk.yunxin.uikit.business.session.emoji;

public interface IEmoticonSelectedListener {
    void onEmojiSelected(String key);

    void onStickerSelected(String categoryName, String stickerName);
}
