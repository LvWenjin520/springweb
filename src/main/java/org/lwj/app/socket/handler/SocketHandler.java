package org.lwj.app.socket.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//websocket处理器
@Component("myHandler")
public class SocketHandler extends TextWebSocketHandler {
	
	
	//获取当前所有用户的集合
	private static Map<String,WebSocketSession> userSessions; 
	
	//这是session的key
	private static final String USER_ID = "WEBSOCKET_USERNAME";
	
	static {
		userSessions = new HashMap<String,WebSocketSession>();
	}
	
	//当前用户建立连接后
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("建立连接");
		
		//获取username
		String userName = (String)session.getAttributes().get(USER_ID);
		
		//放入sessions中
		userSessions.put(userName, session);
		
		System.out.println("当前登录数量为："+userSessions.size());
	}

	
	
	//前端使用send方法时调用
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		super.handleTextMessage(session, message);
		
		//System.out.println("前端发来的消息："+message);
		
		
		//发给th
		/*
		 * if(userSessions.get("th") != null) { userSessions.get("th").sendMessage(new
		 * TextMessage("这是后台发来的消息")); }
		 */
		
		
	}
	

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("关闭连接");
		
		String userName = (String)session.getAttributes().get(USER_ID);
		
		userSessions.remove(userName);
		
		System.out.println("剩余在线人数："+userSessions.size());
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	//单独发送给某人,且不能发给自己
	public int sendMessageToUser(String user,String friendName, TextMessage message) throws Exception {        
		for (String id : userSessions.keySet()) {            
			if(id.equals(friendName)) {                
				try {                    
					if (userSessions.get(id).isOpen()) {                        
						userSessions.get(id).sendMessage(message);                    
						return 1;
					}                
					} catch (IOException e) {                    
						e.printStackTrace();                
					}
				break;
			}
		}
		return 0;    
	}
}

