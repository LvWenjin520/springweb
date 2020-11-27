package org.lwj.app.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.lwj.app.chat.dao.ChatDao;
import org.lwj.app.chat.entity.ChatMsg;
import org.lwj.app.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


//chat的service
@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Autowired
	@Qualifier("chatDao")
	ChatDao chatDao;
	
	@Override
	public List<String> queryFriendsByUserId(int fId) {
		// TODO Auto-generated method stub
		List<String> friends = chatDao.queryFriendsByUserId(fId);
		
		return friends;
	}

	
	//存储消息
	@Override
	public int saveMsg(ChatMsg chatMsg) {
		if(chatDao.saveMsg(chatMsg) > 0) {
			return 1;
		}
		return 0;
	}


	
	//根据用户名查询id
	@Override
	public int queryUserIdByUserName(String userName) {
		return chatDao.queryUserIdByUserName(userName);
	}

	
	//返回聊天记录
	@Override
	public List<ChatMsg> queryUserMsgByUserNameAndFriend(Map<String, Object> map) {
		List<ChatMsg> chatMsgs = chatDao.queryUserMsgByUserNameAndFriend(map);
		return chatMsgs;
	}

	//标记未读消息
	@Override
	public int setFriendMsgToUnRead(Map<String, Integer> map) {
		
        int count = chatDao.setFriendMsgToUnRead(map);
		
		return count;
	}


	//查询未读消息的发件人
	@Override
	public List<Integer> queryUnReadMsg(int userId) {
		
		List<Integer> UnReadFriendsIds = chatDao.queryUnReadMsg(userId);
		
		return UnReadFriendsIds;
	}
	
	//根据id查姓名
	@Override
	public String queryUserNameByUserId(int userId) {
		String friendName = chatDao.queryUserNameByUserId(userId);
		return friendName;
	}


	@Override
	public int setFriendMsgToReaded(Map<String, Object> map) {
		int count = chatDao.setFriendMsgToReaded(map);
		return count;
	}


	
	
	
	
	
	

}
