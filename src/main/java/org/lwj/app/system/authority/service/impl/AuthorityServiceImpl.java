package org.lwj.app.system.authority.service.impl;

import org.lwj.app.login.entity.User;
import org.lwj.app.system.authority.dao.AuthorityDao;
import org.lwj.app.system.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	@Qualifier("authorityDao")
	AuthorityDao authorityDao;
	
	@Override
	public User queryAuthorityByUserId(int id) {
		return authorityDao.queryAuthorityByUserId(id);
	}

}
