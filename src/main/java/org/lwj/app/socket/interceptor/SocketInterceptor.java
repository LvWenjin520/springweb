package org.lwj.app.socket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.lwj.app.login.entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


//webSocket拦截器
@Component
public class SocketInterceptor extends HttpSessionHandshakeInterceptor{

	
	//握手之前的拦截检查方法，就是一个拦截器
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		
		if (request instanceof ServletServerHttpRequest) {
            // 获取请求参数，首先我们要获取HttpServletRequest对象才能获取请求参数；当ServerHttpRequset的层次结构打开后其子类可以获取到我们想要的http对象，那么就简单了。
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			//获取session
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			//获取session中的user
			//User user = (User)session.getAttribute("user");
			//System.out.println(user.getUserName());
			
			if (session != null) {                
				//使用userName区分WebSocketHandler，以便定向发送消息                
				User user = (User)session.getAttribute("user"); 
				String userName = user.getUserName();
				if (userName!=null) {                    
					attributes.put("WEBSOCKET_USERNAME",userName);    
				}             
			}
		}
            //放行
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	
	//握手后
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		System.out.println("握手成功");
        super.afterHandshake(request, response, wsHandler, exception);
	}

}
