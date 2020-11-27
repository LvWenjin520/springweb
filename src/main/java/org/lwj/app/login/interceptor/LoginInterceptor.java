package org.lwj.app.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lwj.app.login.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



public class LoginInterceptor implements HandlerInterceptor {
	
	//最前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//清理缓存,禁止缓存
  		response.setHeader("Pragma","no-cache");    
        response.setHeader("Cache-Control","no-cache");    
        response.setDateHeader("Expires", 0); 
		
        HttpSession session = request.getSession();
		
        //20分钟
		session.setMaxInactiveInterval(60*20);
		
		User user = (User) session.getAttribute("user");
		if(user == null){
			response.sendRedirect("../login/askForLoginPage");
			return false;
		}
		return true;
	}
	
	//after之前，controller之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	
	}
	
	//controller之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}
