package org.lwj.app.chat.entity;

import org.springframework.stereotype.Component;

//聊天记录的表
@Component("chatMsg")
public class ChatMsg {
	private int id;
	private int userId;
	private int friendId;
	private String userMsg;
	private String unReadMsg;
	private String userMsgDate; 
	private String unReadMsgDate;
	
	
	
	
	public ChatMsg(int userId, int friendId, String unReadMsg) {
		this.userId = userId;
		this.friendId = friendId;
		this.unReadMsg = unReadMsg;
	}

	public ChatMsg(int userId, int friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}

	public ChatMsg() {
	}
	
	public ChatMsg(int userId, int friendId, String userMsg, String unReadMsg, String userMsgDate, String unReadMsgDate) {
		this.userId = userId;
		this.friendId = friendId;
		this.userMsg = userMsg;
		this.unReadMsg = unReadMsg;
		this.userMsgDate = userMsgDate;
		this.unReadMsgDate = unReadMsgDate;
	}
	public ChatMsg(int id, int userId, int friendId, String userMsg, String unReadMsg, String userMsgDate,
			String unReadMsgDate) {
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.userMsg = userMsg;
		this.unReadMsg = unReadMsg;
		this.userMsgDate = userMsgDate;
		this.unReadMsgDate = unReadMsgDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public String getunReadMsg() {
		return unReadMsg;
	}
	public void setunReadMsg(String unReadMsg) {
		this.unReadMsg = unReadMsg;
	}
	public String getUserMsgDate() {
		return userMsgDate;
	}
	public void setUserMsgDate(String userMsgDate) {
		this.userMsgDate = userMsgDate;
	}
	public String getunReadMsgDate() {
		return unReadMsgDate;
	}
	public void setunReadMsgDate(String unReadMsgDate) {
		this.unReadMsgDate = unReadMsgDate;
	} 
	
}
