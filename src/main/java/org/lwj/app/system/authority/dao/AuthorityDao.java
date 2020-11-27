package org.lwj.app.system.authority.dao;

import org.lwj.app.login.entity.User;
import org.springframework.stereotype.Repository;

@Repository("authorityDao")
public interface AuthorityDao {
	
	//查询用户权限
	User queryAuthorityByUserId(int id);
	
	//新用户注册添加权限
	boolean addAuthorityByUserId(int id);
	
}
