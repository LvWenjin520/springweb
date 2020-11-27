package org.lwj.app.chat.entity;

import org.springframework.stereotype.Component;

//朋友关系的类
@Component("friendsEntity")
public class Friends {
	private int fId;
	private int userId;
	private int friendId;
	
	public Friends() {
	}
	
	public Friends(int friendId) {
		this.friendId = friendId;
	}
	
	public Friends(int userId, int friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}
	
	public Friends(int fId, int userId, int friendId) {
		this.fId = fId;
		this.userId = userId;
		this.friendId = friendId;
	}
	public int getfId() {
		return fId;
	}
	public void setfId(int fId) {
		this.fId = fId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getfriendId() {
		return friendId;
	}
	public void setfriendId(int friendId) {
		this.friendId = friendId;
	}
}
