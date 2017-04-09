package com.example.win_8.cardigram;

import java.util.Date;

public class ChatMessage {
    public String name;
    public String message;
    public String imagemap;
    public long messageTime;

    public ChatMessage() {
    }

    public ChatMessage(String name, String message, String imagemap) {
        this.name = name;
        this.message = message;
        this.imagemap = imagemap;
        this.messageTime = new Date().getTime();
    }
}