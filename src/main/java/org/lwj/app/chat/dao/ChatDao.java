package org.lwj.app.chat.dao;

import java.util.List;
import java.util.Map;

import org.lwj.app.chat.entity.ChatMsg;
import org.springframework.stereotype.Repository;

//chat的到层
@Repository("chatDao")
public interface ChatDao {
	
	//获取好友名字列表
	List<String> queryFriendsByUserId(int fId);
	
	//获取聊天记录,根据用户以及朋友查询聊天记录
	List<ChatMsg> queryUserMsgByUserNameAndFriend(Map<String,Object> map);

	//存储聊天记录
	int saveMsg(ChatMsg chatMsg);
	
	//根据姓名查id
	int queryUserIdByUserName(String userName);
	
	//设置未读消息
	int setFriendMsgToUnRead(Map<String,Integer> map);
	
	//查询未读消息发来的好友
	List<Integer> queryUnReadMsg(int userId);
	
	//根据id查姓名
	String queryUserNameByUserId(int userId);
	
	//将消息设置为已读
	int setFriendMsgToReaded(Map<String,Object> map);
}
