package org.lwj.app.login.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.lwj.app.login.entity.User;
import org.lwj.app.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 	注册的控制器
 * 
 * 
 * */
@Controller()
@RequestMapping("signup")
public class SignUpController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	
	@ResponseBody
	@PostMapping("/toSignUp")
	public Map<String,String> toSignUp(
			@RequestParam("username") String userName,
			@RequestParam("password") String passWord
			){
		//使用shiro对密码进行加密
		//获取盐，这里使用用户名来产生盐
		Object salt = ByteSource.Util.bytes(userName);
		//对密码进行加密
		SimpleHash simpleHash=new SimpleHash("MD5", passWord, salt, 1);
		User user = new User(userName,simpleHash.toString());
		//设置注册时间
        user.setSignupTime(new Date());
        
        Map<String,String> map = new HashMap<>(); 
        int result = userService.signUpUser(user);
		if(result>0) {
			map.put("flag","注册成功");
			return map;
		}else if(result==0) {
			map.put("flag","用户名重复");
			return map;
		}else if(result == -2){
			map.put("flag","授权失败");
			return map;
		}else{
			map.put("flag","系统异常");
			return map;
		}
	}
}
