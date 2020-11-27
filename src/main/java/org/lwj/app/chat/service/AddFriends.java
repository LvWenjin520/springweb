package org.lwj.app.chat.service;

import java.util.List;
import java.util.Map;

public interface AddFriends {
	//添加请求消息
	int insertAddFriendInfo(String requestUserName,String targetUserName,String addMsg);

	//目标用户是否存在于系统中
	boolean targetUserIsExit(String targetUserName);

	//目标用户是否为好友
	boolean targetUserIsFriend(String userName ,String targetUserName);
	
	//查询好友请求
	List<Map<String,String>> queryFriendsAddInfo(String userName);

	//同意好友请求
	boolean agreeWithFriendsAdd(String requestUserName,String userName);

	//拒绝好友请求
	int defineFriendsAddInfo(Map<String,String> map);
	
}
