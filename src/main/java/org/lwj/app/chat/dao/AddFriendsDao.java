package org.lwj.app.chat.dao;

import java.util.List;
import java.util.Map;

import org.lwj.app.chat.entity.Friends;
import org.lwj.app.chat.entity.FriendsRelationship;
import org.springframework.stereotype.Repository;

//添加好友的dao
@Repository("addFriendsDao")
public interface AddFriendsDao {
	//添加请求消息
	public boolean insertAddFriendInfo(FriendsRelationship friendsRelationship);
	
	//查看目标好友是否已注册
	public int queryTargetUserCount(String targetUserName);
	
	//查询好友是否已经存在
	public int queryHasFriend(Friends friends);
	
	//查询好友添加请求
	public List<Map<String,Object>> queryFriendsAddInfo(int userId);
	
	//将好友请求表中的同意改为1，代表同意
	public boolean agreeWithFriendsAdd(Map<String,Integer> map);
	
	//向好友表中添加关系,从我的角度
	public boolean postRelationshipByMe(Map<String,Integer> map);

	//向好友表中添加关系,从好友的角度
	public boolean postRelationshipByFriend(Map<String,Integer> map);

	//删除好友请求信息，拒绝好友请求
	public boolean deleteFriendsAddInfo(Map<String,Integer> map);


}
