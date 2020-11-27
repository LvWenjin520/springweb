package org.lwj.app.socket.config;

import org.lwj.app.socket.handler.SocketHandler;
import org.lwj.app.socket.interceptor.SocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


//websocket配置文件
@Configuration
@EnableWebSocket
public class Config implements WebSocketConfigurer {
    
	
	@Autowired
    private SocketHandler myHandler;

    @Autowired
    private SocketInterceptor myInterceptor;

    //这里配置
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	registry.addHandler(myHandler, "/websocket")
            .addInterceptors(myInterceptor)
            .setAllowedOrigins("*");
        
        registry.addHandler(myHandler, "/websocket")
	        .addInterceptors(myInterceptor)
	        .setAllowedOrigins("http://localhost:8080").withSockJS();;
        
    }
    
	 @Bean 
	 public TextWebSocketHandler webSocketHandler(){ 
		 return new SocketHandler(); 
	 }
	 
}
