package com.example.softteam.messages;

public class MessageList {
    private final String name;
    private final String mobile;
    private final String lastMessage;
    private final String profileUrl;
    private final int unseenMessages;

    public MessageList(String name, String mobile, String lastMessage, String profileUrl, int unseenMessages,String chatKey) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.profileUrl = profileUrl;
        this.unseenMessages = unseenMessages;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public boolean getChatKey() {
        return false;
    }
}
