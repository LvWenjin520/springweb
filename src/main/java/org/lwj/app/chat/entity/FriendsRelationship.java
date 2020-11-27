package org.lwj.app.chat.entity;

import org.springframework.stereotype.Component;


//好友请求实体类
@Component
public class FriendsRelationship {
	
	private int id;
	private int requestUserId;
	private int targetUserId;
	private String addMsg;
	private int agreement;  //是否同意，0为不同意，1为同意
	private String sendMsg;
	
	
	


	public FriendsRelationship(int requestUserId, String addMsg) {
		this.requestUserId = requestUserId;
		this.addMsg = addMsg;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public FriendsRelationship() {
	}
	
	
	public FriendsRelationship(int requestUserId, int targetUserId, String addMsg, int agreement, String sendMsg) {
		this.requestUserId = requestUserId;
		this.targetUserId = targetUserId;
		this.addMsg = addMsg;
		this.agreement = agreement;
		this.sendMsg = sendMsg;
	}
	
	
	
	public FriendsRelationship(int id, int requestUserId, int targetUserId, String addMsg, int agreement,
			String sendMsg) {
		this.id = id;
		this.requestUserId = requestUserId;
		this.targetUserId = targetUserId;
		this.addMsg = addMsg;
		this.agreement = agreement;
		this.sendMsg = sendMsg;
	}
	public int getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(int requestUserId) {
		this.requestUserId = requestUserId;
	}
	public int getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getAddMsg() {
		return addMsg;
	}
	public void setAddMsg(String addMsg) {
		this.addMsg = addMsg;
	}
	public int getAgreement() {
		return agreement;
	}
	public void setAgreement(int agreement) {
		this.agreement = agreement;
	}
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	
	
}
