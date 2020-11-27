package org.lwj.app.system.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.lwj.app.login.entity.User;
import org.lwj.app.login.service.UserService;
import org.lwj.app.system.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


//自定义的认证和授权的类
public class MyRealm extends AuthorizingRealm{

	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("authorityService")
	AuthorityService authorityService;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		System.out.println("正在权限，用户为："+userName);
		//需要返回的权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		
		
		
		//获取角色
		Set<String> roles = new HashSet<>();
		
		int userId = userService.queryUserIdByUserName(userName);
		User authorityUser = authorityService.queryAuthorityByUserId(userId);
		if(null != authorityUser) {
			for(int i =0;i<authorityUser.getAuthority().size();i++) {
				//添加这个用户的角色
				roles.add(authorityUser.getAuthority().get(i).getRole_name());
			}
		}
		//行为权限
		Set<String> permissions = new HashSet<>();
		
		//这是获取缓存
		//CacheManager cacheManager = getCacheManager();
		//Cache<Object, Object> cache = cacheManager.getCache(userName);
		//System.out.println(cache);
		
		permissions.add("see");
		info.addRoles(roles);
		info.addStringPermissions(permissions);
		//获取相应的行为权限
		System.out.println("授权成功");
		return info;
	}

	
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证中-->用户为："+token.getPrincipal());
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		String password = userService.queryPasswordByUserName(upToken.getUsername());
		if(null == password) {
			throw new UnknownAccountException();
		}
		//匹配前先加盐再加密
		ByteSource credentialsSalt = ByteSource.Util.bytes(upToken.getUsername());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(upToken.getUsername(), password, this.getName());
		//添加盐值
		info.setCredentialsSalt(credentialsSalt);
		//服务器令牌，用来认证用
		return info;
	}
	
	//清理缓存,后期在做权限控制的时候调用此方法，当修改了权限后，刷新缓存，将修改生效
	public void clearCache() {
		  Subject currentUser = SecurityUtils.getSubject();
		  super.clearCache(currentUser.getPrincipals());
	}

}
