package org.lwj.app.login.dao;

import org.lwj.app.login.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao {
	
	//根据姓名和密码查询用户名及密码
	int queryUserByUserNameAndPassword(User user);
	
	//注册用户  返回该用户的主键值
	int insertUserToSignup(User user);
	
	//根据姓名查询
	int queryUserByUserName(String userName);
	
	//根据姓名查id
	int queryUserIdByUserName(String userName);
	
	//根据姓名查密码
	String queryPasswordByUserName(String userName);
	
}
