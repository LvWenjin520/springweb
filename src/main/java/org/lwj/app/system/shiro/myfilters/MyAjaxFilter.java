package org.lwj.app.system.shiro.myfilters;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


//自定义扩展的过滤器，用于过滤没有通过检验的ajax请求
public class MyAjaxFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}
	

	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String ajaxHeader = httpRequest.getHeader("x-requested-with");
		if (!subject.isAuthenticated() && ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest"))
		{
			/**
			  * 当前请求为AJAX请求，但会话已失效
			 */
			// 保存请求
			saveRequest(request);
			// 设置响应头
			httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
			// 响应信息
			httpResponse.getWriter().print("会话已失效");
			httpResponse.getWriter().close();
			return false;
		}
		return true;
	}
	



	
	
}
