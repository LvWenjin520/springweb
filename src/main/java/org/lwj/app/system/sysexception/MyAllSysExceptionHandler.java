package org.lwj.app.system.sysexception;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常处理

@ControllerAdvice
public class MyAllSysExceptionHandler {

	
	//如果没有权限的话统一在这里处理这个异常
	@ResponseBody
	@ExceptionHandler(UnauthorizedException.class)
	public Map<String,String> sysExceptionHandler(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("flag", "false");
		map.put("msg", "你没有权限！请联系管理员");
		return map;
	}
	
}
