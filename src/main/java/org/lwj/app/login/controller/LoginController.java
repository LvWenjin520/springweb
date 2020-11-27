package org.lwj.app.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.lwj.app.login.entity.User;
import org.lwj.app.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@GetMapping("askForLoginPage")
	public String toLoginPage() {
		return "loginPage";
	}
	
	//登录的判断
	//返回json类型的数据
	@ResponseBody
	@PostMapping("/toLogin")
	public Map<String,String> loginControl(
			@RequestParam("userName") String username,
			@RequestParam("passWord") String password,
			HttpServletRequest request
			) throws IncorrectCredentialsException {
		
		User user = new User(username,password);
		
		
		
		Map<String,String> msgMap = new HashMap<>();
		System.out.println(username+":"+password);
		//获取当前用户请求
        Subject currentUser = SecurityUtils.getSubject();
        
        Session session = currentUser.getSession();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		 
        
        token.setRememberMe(true);
        
        if(!currentUser.isAuthenticated()) {
        	try {
                currentUser.login(token);
                System.out.println("登录成功");
                user.setId(userService.queryUserIdByUserName(username));
        		user.setPassWord("no way!!!");
                session.setAttribute("user", user);
                msgMap.put("flag", "success");
                msgMap.put("path", "views/userIndex.html");
                return msgMap;
            }
            catch(UnknownAccountException ue){
            	System.out.println("用户不存在");
            	msgMap.put("flag", "用户不存在");
            	return msgMap;
            }
            catch (IncorrectCredentialsException ice) {// 密码不匹配异常
            	System.out.println("密码错误");
            	msgMap.put("flag", "密码错误");
            	return msgMap;
            }
            catch (LockedAccountException lae) {// 用户被锁定
            	System.out.println("用户被锁定");
            	msgMap.put("flag", "用户被锁定");
            	return msgMap;
            }
            catch (AuthenticationException ae) {
                System.out.println("系统异常请联系管理员 ");
                msgMap.put("flag", "系统异常请联系管理员");
            	return msgMap;
            }
        }else {
        	msgMap.put("flag", "success");
        	user.setId(userService.queryUserIdByUserName(username));
    		user.setPassWord("no way!!!");
        	session.setAttribute("user", user);
            msgMap.put("path", "views/userIndex.html");
        	return msgMap;
        }
        
        
	}
}
