package org.lwj.app.chat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwj.app.chat.dao.AddFriendsDao;
import org.lwj.app.chat.dao.ChatDao;
import org.lwj.app.chat.entity.Friends;
import org.lwj.app.chat.entity.FriendsRelationship;
import org.lwj.app.chat.service.AddFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("addFriendsService")
public class AddFriendsImpl implements AddFriends {
	
	@Autowired
	@Qualifier("addFriendsDao")
	AddFriendsDao addFriendsDao;
	
	@Autowired
	@Qualifier("chatDao")
	ChatDao chatDao;
	
	
	//添加请求消息
	@Override
	public int insertAddFriendInfo(String requestUserName,String targetUserName,String addMsg) {
		//当目标用户存在且用户没有添加过此好友时
		if(! this.targetUserIsExit(targetUserName)) {
			return -1;   //此用户不存在
		}else if(requestUserName.equals(targetUserName)) {
			return -2;   //添加自己为好友时
		}else if(this.targetUserIsFriend(requestUserName, targetUserName)) {
			return 0;  //此用户已经时好友
		}else {
			FriendsRelationship friendsRelationship = new FriendsRelationship();
			
			int requestUserId = chatDao.queryUserIdByUserName(requestUserName);
			int targetUserId = chatDao.queryUserIdByUserName(targetUserName);
			
			friendsRelationship.setRequestUserId(requestUserId);
			friendsRelationship.setTargetUserId(targetUserId);
			friendsRelationship.setAddMsg(addMsg);
			addFriendsDao.insertAddFriendInfo(friendsRelationship);
			// TODO Auto-generated method stub
			return 1;    //好友请求发送成功
		}
	}

	
	//判断目标好友是否存在
	@Override
	public boolean targetUserIsExit(String targetUserName) {
		if(addFriendsDao.queryTargetUserCount(targetUserName) == 1) {
			return true;  //目标好友存在
		}
		return false;
	}


	//目标用户是否已经是好友
	@Override
	public boolean targetUserIsFriend(String userName, String targetUserName) {
		
		int userId = chatDao.queryUserIdByUserName(userName);
		int targetUserId = chatDao.queryUserIdByUserName(targetUserName);
		
		Friends friends = new Friends(userId, targetUserId);
		
		if(addFriendsDao.queryHasFriend(friends)>0) {
			return true;   //已经有了此好友
		}
		return false;
	}

	//查询好友请求信息
	@Override
	public List<Map<String,String>> queryFriendsAddInfo(String userName) {
		// TODO Auto-generated method stub
		
		int userId = chatDao.queryUserIdByUserName(userName);
		
		List<Map<String,Object>> list = addFriendsDao.queryFriendsAddInfo(userId);
		
		 
		
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		Map<String,String> resultMap = new HashMap<>();
		if(list.size() > 0) {
			
			for(Map<String, Object> map : list) {
				String requestUserName = chatDao.queryUserNameByUserId((int) map.get("requestUserId"));
				
				resultMap.put("requestUserName", requestUserName);
				resultMap.put("addMsg",map.get("addMsg").toString());
				
				resultList.add(resultMap);
				
				
			}
			return resultList;
		}else {
			return resultList;
		}
		
	}


	//同意好友的请求
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public boolean agreeWithFriendsAdd(String requestUserName,String userName) {
		// TODO Auto-generated method stub
		int userId= chatDao.queryUserIdByUserName(userName);
		int requestUserId = chatDao.queryUserIdByUserName(requestUserName);
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("requestUserId", requestUserId);
		
		//将表标识符变为同意
		boolean flag = addFriendsDao.agreeWithFriendsAdd(map);
		
		//从自己的角度写入好友关系
		if(flag) {
			addFriendsDao.postRelationshipByMe(map);
			addFriendsDao.postRelationshipByFriend(map);
			return true;
		}
		return false;
	}


	@Override
	public int defineFriendsAddInfo(Map<String,String> map) {

		int requestUserId = chatDao.queryUserIdByUserName(map.get("requestUserName"));
		int targetUserId = chatDao.queryUserIdByUserName(map.get("targetUserName"));
		
		Map<String,Integer> paramMap = new HashMap<>();
		
		paramMap.put("requestUserId", requestUserId);
		paramMap.put("targetUserId", targetUserId);
		boolean flag = addFriendsDao.deleteFriendsAddInfo(paramMap);
		
		if(flag) {
			return 1;  //删除成功
		}
		return 0;  //删除失败
	}
}
