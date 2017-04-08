package com.example.win_8.cardigram;

import java.util.Date;

/**
 * Created by win-8 on 08-04-2017.
 */

public class GroupMessage {

	private String messageText;
	private String messageUser;
	private long messageTime;

	public GroupMessage(String messageText, String messageUser) {
		this.messageText = messageText;
		this.messageUser = messageUser;

		// Initialize to current time
		messageTime = new Date().getTime();
	}

	public GroupMessage(){

	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageUser() {
		return messageUser;
	}

	public void setMessageUser(String messageUser) {
		this.messageUser = messageUser;
	}

	public long getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(long messageTime) {
		this.messageTime = messageTime;
	}
}