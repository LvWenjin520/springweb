package org.lwj.app.login.service.impl;

import org.lwj.app.login.dao.UserDao;
import org.lwj.app.login.entity.User;
import org.lwj.app.login.service.UserService;
import org.lwj.app.system.authority.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@Autowired
	@Qualifier("authorityDao")
	AuthorityDao authorityDao;
	
	
	
	@Override
	public int queryUserByUserName(User user) {
		int count = userDao.queryUserByUserNameAndPassword(user);
		if(count == 1) {
			//查到了一个，说明有该用户，放行
			return 1;
		}else if(count == 0) {
			//没有查到，没有该用户，不放行
			return 0;
		}else {
			//系统异常
			return -1;
		}
	}

	
	//注册用户，注册前先检查是否重复
	@Override
	@Transactional
	public int signUpUser(User user) {
		int result = queryUserByUserName(user.getUserName());
		//没有重复就注册
		if(result == 1) {
			int count = userDao.insertUserToSignup(user);
			int userId = this.queryUserIdByUserName(user.getUserName());
			boolean flag = authorityDao.addAuthorityByUserId(userId);
			if(flag) {
				return count;
			}
			return -2;
		}else if(result == 0) {
			return 0;  //有重复就不插入
		}else {
			return -1;  //系统异常
		}
	}

	
	//根据姓名查询，避免重复
	@Override
	public int queryUserByUserName(String userName) {
		int userId = userDao.queryUserByUserName(userName);
		if(userId > 0) {
			return 0;  //有重复
		}else if(userId == 0) {
			return 1;  //无重复
		}else {
			return -1;  //系统异常
		}
	}


	@Override
	public int queryUserIdByUserName(String userName) {
		return userDao.queryUserIdByUserName(userName);
	}


	@Override
	public String queryPasswordByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.queryPasswordByUserName(userName);
	}


	@Override
	public boolean addAuthorityByUserId(int id) {
		return authorityDao.addAuthorityByUserId(id);
	}
}
