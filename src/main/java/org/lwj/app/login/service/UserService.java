package org.lwj.app.login.service;

import org.lwj.app.login.entity.User;

public interface UserService {
	//查询用户
	int queryUserByUserName(User user);
	
	//注册用户  返回该用户的主键值
	int signUpUser(User user);
	
	//根据姓名查询用户，避免重复
	int queryUserByUserName(String userName);
	
	//根据姓名查id
	int queryUserIdByUserName(String userName);
	
	//根据姓名查密码
	String queryPasswordByUserName(String userName);
	
	//新用户注册分配普通用户角色
	boolean addAuthorityByUserId(int id);
}
