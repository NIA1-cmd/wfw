package com.oncloudsoft.sdk.yunxin.uikit.impl.cache;

import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

/**
 * Created by hzchenkang on 2017/10/18.
 * <p>
 * 聊天室相关业务缓存生命周期管理
 */

public class ChatRoomCacheManager {

    private static boolean enableCache;

    static {
        boolean userChatRoom = false;
        try {
            Class.forName("com.netease.nimlib.sdk.chatroom.ChatRoomService");
            userChatRoom = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        enableCache = userChatRoom && com.oncloudsoft.sdk.yunxin.uikit.impl.NimUIKitImpl.getOptions().buildChatRoomMemberCache;
    }

    public static void initCache() {
        if (enableCache) {
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().clear();
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().registerObservers(true);
        }
    }

    public static void clearCache() {
        if (enableCache) {
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().registerObservers(false);
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().clear();
        }
    }

    public static void clearRoomCache(String roomId) {
        if (enableCache) {
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        }
    }

    public static void saveMyMember(ChatRoomMember member) {
        if (enableCache) {
            com.oncloudsoft.sdk.yunxin.uikit.impl.cache.ChatRoomMemberCache.getInstance().saveMyMember(member);
        }
    }
}
