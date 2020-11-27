package org.lwj.app.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.lwj.app.chat.service.AddFriends;
import org.lwj.app.chat.service.ChatService;
import org.lwj.app.login.entity.User;
import org.lwj.app.socket.handler.SocketHandler;
import org.lwj.utils.jsonutils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

//添加好友的控制器
@Controller
@RequestMapping("friends")
public class AddFriendsController {
	
	@Autowired
	@Qualifier("addFriendsService")
	AddFriends addFriendsService;
	
	@Autowired
	@Qualifier("chatService")
	ChatService chatService;
	
	@Autowired
	@Qualifier("myHandler")
	SocketHandler socketHandler;
	
	//向好友关系表中添加请求消息
	@PostMapping("/friends/{friendName}")
	@ResponseBody
	@RequiresRoles(value= {"normalUser","super"},logical=Logical.OR)
	public Map<String,String> addFriends(
			@PathVariable("friendName") String friendName,
			@RequestParam("addMsg") String addMsg,
			HttpServletRequest request
			) throws Exception{
		
		
		
		//向添加好友关系表中插入数据
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userName = user.getUserName();
		//写入数据库
		int result = addFriendsService.insertAddFriendInfo(userName, friendName, addMsg);
		
		
		Map<String,String> map = new HashMap<String, String>();
		
		if(result == 0) {
			map.put("flag", "false");
			map.put("msg", "此用户已经是好友了");
			return map;
		}else if(result == 1) {
			map.put("flag", "success");
			map.put("msg", "发送成功");
			
			//将msg封装成json格式的字符串
			Map<String,String> friendAddMsg = new HashMap<>();
			friendAddMsg.put("requestUserName", userName);
			friendAddMsg.put("addMsg", addMsg);
			String message = JsonUtils.jsonToString(friendAddMsg);
			//发送给好友，前端接收验证消息
			socketHandler.sendMessageToUser(userName, friendName, new TextMessage(message));
			return map;
		}else if(result == -2) {
			map.put("flag", "false");
			map.put("msg", "请不要添加自己为好友");
			return map;
		}
		
		map.put("flag", "false");
		map.put("msg", "目标好友不存在");
		
		return map;
	}
	
	
	//拒绝好友请求
	@DeleteMapping("/friends/{friendName}")
	@ResponseBody
	@RequiresRoles(value= {"normalUser","super"},logical=Logical.OR)
	public Map<String,String> defineAddMsg(
			@PathVariable("friendName") String requestUserName,
			HttpServletRequest request
			){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String targetUserName = user.getUserName();
		
		Map<String,String> paramMap = new HashMap<>();
		
		paramMap.put("requestUserName", requestUserName);
		paramMap.put("targetUserName", targetUserName);
		
		//删除好友请求信息
		int flag = addFriendsService.defineFriendsAddInfo(paramMap);
		Map<String,String> map = new HashMap<>();
		if(flag == 0) {
			map.put("flag", "false");
			map.put("msg", "系统异常");
			return map;
		}
		map.put("flag", "success");
		map.put("msg", "拒绝成功");
		return map;
	}
	
	
	
	//获取用户请求的信息
	@GetMapping("/friends")
	@ResponseBody
	public List<Map<String,String>> getAddFriendsInfo(
			HttpServletRequest request
			){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userName = user.getUserName();
		
		List<Map<String,String>> result = addFriendsService.queryFriendsAddInfo(userName);
		if(result.size() > 0) {
			return result;
		}else {
			Map<String,String> map = new HashMap<>();
			map.put("flag", "noAddInfo");
			result.add(map);
			return result;
		}
	} 
	
	
	//同意用户的好友请求
	@RequiresRoles(value= {"normalUser","super"},logical=Logical.OR)
	@PutMapping("/friends/{requestUserName}")
	@ResponseBody
	public Map<String,String> agreeWithUser(
			@PathVariable("requestUserName") String requestUserName,
			@RequestParam("addMsg") String addMsg,
			HttpServletRequest request
			) throws Exception{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String userName = user.getUserName();
		
		boolean flag = addFriendsService.agreeWithFriendsAdd(requestUserName,userName);
		
		Map<String,String> map = new HashMap<String, String>();
		
		//同意成功
		if(flag) {
			
			//向请求好友发送标志，我已同意
			Map<String,String> flagMsg = new HashMap<String, String>();
			flagMsg.put("addFlagMsg", "agree");
			String agreeMsg = JsonUtils.jsonToString(flagMsg);
			socketHandler.sendMessageToUser(userName, requestUserName, new TextMessage(agreeMsg));
			
			map.put("flag","success");
			map.put("msg","添加成功");
			return map;
		}else {
			map.put("flag","false");
			map.put("msg","系统异常");
			return map;
		}
	}
}
