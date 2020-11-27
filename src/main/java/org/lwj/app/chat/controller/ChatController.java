package org.lwj.app.chat.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.lwj.app.chat.entity.ChatMsg;
import org.lwj.app.chat.service.ChatService;
import org.lwj.app.login.entity.User;
import org.lwj.app.socket.handler.SocketHandler;
import org.lwj.utils.dateutils.DateUtils;
import org.lwj.utils.jsonutils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

@Controller
@RequestMapping("chat")
public class ChatController {

	
	//自动装配自定义的socketHandler
	@Autowired
	SocketHandler myHandler;
	
	@Autowired
	@Qualifier("chatMsg")
	ChatMsg chatMsg;
	
	@Autowired
	@Qualifier("chatService")
	ChatService chatService;
	
	@RequestMapping("/toChatPage")
	public String toChatPage() {
		return "chat";
	}
	
	
	//获取好友列表
	@RequiresRoles(value= {"normalUser","super"},logical=Logical.OR)  //或
	@RequestMapping("/getMyFriends")
	@ResponseBody
	public Map<String,List<String>> getFriendsList(
			HttpServletRequest request
			){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		List<String> friends = chatService.queryFriendsByUserId(user.getId());
		Map<String,List<String>> friendsMap = new HashMap<>();
		friendsMap.put("friends",friends);
		List<String> userName = new ArrayList<>();
		userName.add(user.getUserName());
		friendsMap.put("userName",userName);
		return friendsMap;
	}
	
	//发送消息
	@RequestMapping("/sendMsg")
	@ResponseBody
	public String send(
			@RequestParam("toUserName") String toUserName,
			@RequestParam("msg") String msg,
			HttpServletRequest request
			) throws Exception {
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		String nowUser = user.getUserName();
		
		//获取当前用户的id，就是发送人的id
		int userId=user.getId();
		
		//获取好友id
		int friendId = chatService.queryUserIdByUserName(toUserName);
		
		
		chatMsg.setUserId(userId);  //设置发送人id
		chatMsg.setFriendId(friendId);  //设置好友id
		chatMsg.setUserMsg(msg);  //设置发送消息
		chatMsg.setunReadMsg(null);  //设置好友信息
		chatMsg.setUserMsgDate(DateUtils.formatDate(new Date()));
		chatMsg.setunReadMsgDate(null);
		//存储消息
		int count = chatService.saveMsg(chatMsg);
		
		
		Map<String,String> result = new HashMap<>();
		
		result.put("fromUserName",nowUser);
		result.put("msg",msg);
		
		//将json对象转为json字符串,前端用来接收的消息再转为json对象处理
		msg = JsonUtils.jsonToString(result);
		
		//发送消息
		if(count>0) {
			int flag = myHandler.sendMessageToUser(nowUser,toUserName,new TextMessage(msg));
			if(flag==1) {
				return null;
			}else {
				Map<String,Integer> map = new HashMap<>();
				map.put("userId",userId);
				map.put("friendId",friendId);
				chatService.setFriendMsgToUnRead(map);
			}
		}
		return null;
	} 
	 
	//获取聊天记录
	@PostMapping("/getMsg")
	@ResponseBody
	public Map<String,List<ChatMsg>> getMyFriendMsg(
			@RequestParam("friendName") String friendName,
			HttpServletRequest request
			) throws ParseException{
		
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		String userName = user.getUserName();
		
		int userId = chatService.queryUserIdByUserName(userName);
		int friendId = chatService.queryUserIdByUserName(friendName);
		
		Map<String,Object> friendAndMe = new HashMap<>();
		
		friendAndMe.put("userId",userId);
		friendAndMe.put("friendId",friendId);
		friendAndMe.put("limitDate",DateUtils.limitDate());
		
		List<ChatMsg> friendMsgs = chatService.queryUserMsgByUserNameAndFriend(friendAndMe);
		List<ChatMsg> userInform = new ArrayList<>();
		
		userInform.add(new ChatMsg(userId,friendId));
		
		//将未读消息变为已读状态
		chatService.setFriendMsgToReaded(friendAndMe);
		
		Map<String,List<ChatMsg>> resultMap = new HashMap<>();
		
		resultMap.put("msgs",friendMsgs);
		resultMap.put("ids",userInform);
		return resultMap;
	}


	//获取未读消息
	@PostMapping("/getUnReadMsg")
	@ResponseBody
	public Map<String,List<String>> getUnReadMsg(
			HttpServletRequest request
			){
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		String userName = user.getUserName();
		
		int userId = chatService.queryUserIdByUserName(userName);
		
		List<Integer> unReadFriends = chatService.queryUnReadMsg(userId);
		
		List<String> friends = new ArrayList<>();
		
		String friendName;
		
		for(int i=0;i<unReadFriends.size();i++) {
			friendName = chatService.queryUserNameByUserId(unReadFriends.get(i));
			friends.add(friendName);
		}
		
		Map<String,List<String>> map = new HashMap<>();
		map.put("result",friends);
		return map;
	}

}
