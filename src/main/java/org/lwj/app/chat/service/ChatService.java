package org.lwj.app.chat.service;

import java.util.List;
import java.util.Map;

import org.lwj.app.chat.entity.ChatMsg;

public interface ChatService {
	//获取好友
	List<String> queryFriendsByUserId(int fId);
	
	//获取聊天记录
	List<ChatMsg> queryUserMsgByUserNameAndFriend(Map<String,Object> map);
	
	//存储消息
	int saveMsg(ChatMsg chatMsg);
	
	//查询id
	int queryUserIdByUserName(String userName);

	//设置未读消息
	int setFriendMsgToUnRead(Map<String,Integer> map);
	
	//查询未读消息
	List<Integer> queryUnReadMsg(int userId);
	
	//根据id查姓名
	String queryUserNameByUserId(int userId);
	
	//将消息设置为已读
	int setFriendMsgToReaded(Map<String,Object> map);
	
	
	
}
