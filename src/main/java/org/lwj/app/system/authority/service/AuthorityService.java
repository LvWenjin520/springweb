package org.lwj.app.system.authority.service;

import org.lwj.app.login.entity.User;

public interface AuthorityService {
	User queryAuthorityByUserId(int id);
}
