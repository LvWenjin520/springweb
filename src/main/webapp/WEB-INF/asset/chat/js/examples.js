

$(function () {
	
//查询并展示我的好友
getMyFriends();

//开启websocket连接
connectService();

//好友列表双击事件
friendsEvents();

//聊天列表单击事件
getChatBodyAndMsg();

//发送消息事件
sendMsgEvent();
//添加查询未读消息事件
queryUnReadMsgEvent();

//添加好友，发送好友请求
addFriends();


//获取好友添加请求的消息
getAddFriendsInfo();

//给好友请求列表的按钮绑定事件
addFriendsBtnEvents();

//好友请求列表的事件，点击弹出模态框，选择同意或拒绝
addInfoEvents();
});

//添加查询未读消息事件
function queryUnReadMsgEvent(){
	$('.allFriendsListBtn').on('click',function(){
		$("#chatBody").empty();
		$("#myChatList").children().removeClass("open-chat");
		getUnReadMsg();
	})
}

//查询未读消息
function getUnReadMsg(){
	$.ajax({
		type:'POST',
		url:'../chat/getUnReadMsg',
		async:true,
		success:function(data){
			console.log(data.result);
			var ele = $("#myFriendsList").children().find('h5');
			ele.each(function(index,e){
				//如果找到了发送消息的好友
				if($.inArray($(e).html(),data.result)>=0){
					$(e).parent().parent().css('backgroundColor','green');
				}else if($.inArray($(e).html(),data.result)<0){
					$(e).parent().parent().css('backgroundColor','white');
				}
			});
		}
	});
}

//查询我的朋友
function getMyFriends(){
	$.ajax({
		type:"GET",
		url:"../chat/getMyFriends",
		success:function(result){
			
			if(result.msg != undefined){
				alert(result.msg);
				return;
			}
			
			var friends = result.friends;
			//用户的名字
			putUserName(result.userName[0]);
			
			for(var i = 0;i<friends.length;i++){
				addFriendsList(friends[i]);
			}
			
			//查询未读消息
			getUnReadMsg();
		}
	});
}
	
//给用户名赋值
function putUserName(userName){
	$("#myName").html(userName);// 填充内容
}
	
//动态添加好友列表
function addFriendsList(friendsName){
$("#myFriendsList").append(
		$("<li>",{
			'class':'list-group-item'
		}).append(
			$("<div>").append(
				$("<figure>",{
					'class':'avatar'
				}).append(
					$("<img>",{
						'class':'rounded-circle',
						'src':'../asset/chat/imgs/women_avatar5.jpg'
					})
				)
			),
			$("<div>",{
				'class':'users-list-body'
			}).append(
				$("<h5>",{
					'html':friendsName,
					'class':'myFriendName'
				}),
				$("<p>",{
					'html':'个性签名'
				}),
				$("<div>",{
					'class':'users-list-action action-toggle'
				}).append(
					$("<div>",{
						'class':'dropdown'
					}).append(
						$("<a>",{
							'data-toggle':'dropdown',
							'href':'#'
						}).append(
							$("<i>",{
								'class':'ti-more'
							})
						),
						$("<div>",{
							'class':'dropdown-menu dropdown-menu-right'
						}).append(
							$("<a>",{
								'class':'dropdown-item',
								'html':'Open',
								'href':'#'
							}),
							$("<a>",{
								'class':'dropdown-item',
								'data-navigation-target':'contact-information',
								'html':'Profile',
								'href':'#'
							}),
							$("<a>",{
								'class':'dropdown-item',
								'html':'Add to archive',
								'href':'#'
							}),
							$("<a>",{
								'class':'dropdown-item',
								'html':'Delete',
								'href':'#'
							})
						)
					)
				)
			)
		)
	);
}

//动态添加聊天列表好友
function addChatList(friendName){
	//console.log($("#myChatList").find('h5'));
	$("#myChatList").prepend(   //添加到第一个
			$("<li>",{
				'class':'list-group-item'
			}).append(
				$("<div>").append(
					$("<figure>",{
						'class':'avatar'
					}).append(
						$("<img>",{
							'class':'rounded-circle',
							'src':'../asset/chat/imgs/women_avatar5.jpg'
						})
					)
				),
				$("<div>",{
					'class':'users-list-body'
				}).append(
					$("<h5>",{
						'html':friendName
					}),
					$("<p>",{
						'html':'个性签名'
					}),
					$("<div>",{
						'class':'users-list-action action-toggle'
					}).append(
						$("<div>",{
							'class':'dropdown'
						}).append(
							$("<a>",{
								'data-toggle':'dropdown',
								'href':'#'
							}).append(
								$("<i>",{
									'class':'ti-more'
								})
							),
							$("<div>",{
								'class':'dropdown-menu dropdown-menu-right'
							}).append(
								$("<a>",{
									'class':'dropdown-item',
									'html':'Open',
									'href':'#'
								}),
								$("<a>",{
									'class':'dropdown-item',
									'data-navigation-target':'contact-information',
									'html':'Profile',
									'href':'#'
								}),
								$("<a>",{
									'class':'dropdown-item',
									'html':'Add to archive',
									'href':'#'
								}),
								$("<a>",{
									'class':'dropdown-item',
									'html':'Delete',
									'href':'#'
								})
							)
						)
					)
				)
			)
		);
}

//好友列表好友的双击事件，用事件委派绑定
function friendsEvents(){
	//当好友列表中的好友被双击之后，左侧好友列表切换到聊天列表
	$("#myFriendsList").on('dblclick','.list-group-item',function(e){
		var ele = $(e.target);
		while(!ele.is('.list-group-item')){
			ele = ele.parent();
		}
		
		
		//class=notifiy_badge  这是小红点
		$('#friends').removeClass('active');
		$('#chats').addClass('active');
		$('.chatListBtn').addClass('active');
		$('.allFriendsListBtn').removeClass('active');
		$(ele).css("background-color","white");
		var result = $(ele[0].children[1].children[0]);
		
		//当聊天列表中存在好友时，双击将不再重复添加
		if($("#myChatList").find('h5').length<=0){
			addChatList(result.html());
		}else{
			var friendsNames = [];
			for(var i=0;i<$("#myChatList").find('h5').length;i++){
				friendsNames.push($("#myChatList").find('h5')[i].innerHTML);
			}
			if($.inArray(result.html(),friendsNames)<0){
				addChatList(result.html());
			}else{
				return;
			}
		}
		
		$("#chatSidebarBody").css('overflow-y','auto');
		
		//重置选中状态
		//$('li').removeClass('open-chat');
		
		
		
		//好友的名字
		//var result = $(ele[0].children[1].children[0]);
		//发送消息的方法
		//sendMsg(result.html());
		//获取聊天记录
		//getChatMessage(result.html());
		//ele.addClass='open-chat';
	});
}

//获取各种路径
function getRootPath() {
    //获取当前网址，
    var curPath = window.document.location.href;
    //获取主机地址之后的目录，
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    //获取主机地址
    var localhostPaht = curPath.substring(0, pos);
    //获取带"/"的项目名，
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return(localhostPaht+projectName);
}

//发送消息
function sendMsgEvent(){
	$("#sendMsgBtn").click(function(){
		var p = $("[class='list-group-item open-chat']");
		var friendName = $(p[0].children[1].children[0]).html();
		//发送消息的方法
		var msg = $('#msgInput').val();
		if($('#msgInput').val() != ''){
			$.ajax({
				url:'../chat/sendMsg',
				data:{
					msg :msg,  //发送的消息
					toUserName :friendName,  //发给谁
				},
				async:true
			});
			//将发送的消息摆在聊天记录里
			putMyChatMsg($('#msgInput').val(),getDate());
			$('#msgInput').val('');
		}else{
			alert("发送内容无法为空！");
			return;
		}
	});
}

//创建websocket连接
function connectService(){
	//创建websocket连接
	var webSocket = new WebSocket("ws://localhost:8080/SpringWeb/websocket");
	
	//连接成功时，触发此方法
	webSocket.onopen = function(event){
	    console.log("与服务器连接成功");
	    //console.log(event);
	    //webSocket.send("this is the websocket client.");
	};
	webSocket.onerror = function(event){
	    console.log("连接失败，可能是系统异常，请联系管理员");
	    //console.log(event);
	};
	webSocket.onclose = function(event){
	    alert("连接已断开，请刷新页面或联系管理员！");
	    console.log(event);
	};
	//接收消息
	webSocket.onmessage = function(event){
		
		//将后台传来的json字符串转为json对象
		var msgDate =  $.parseJSON(event.data);
		if(msgDate.msg != undefined){
			//获取发送信息的好友的名字
			var fromUserName = msgDate.fromUserName;
			var msg = msgDate.msg;
			
			//|| $.inArray(fromUserName,friendsNames)<0
			var names = [];
			
			for(var i=0;i<$("#myChatList").find("h5").length;i++){
				names.push($("#myChatList").find('h5')[i].innerHTML);
			}
			
			
			//判断如果聊天界面没有打开的话，就不放置消息
			if($("#myChatList").find('h5').length<=0 || $.inArray(fromUserName,names)<0){
				//这里是如果用户登录了，但没有打开聊天页面的提醒
				//这是在好友列表中的提醒
				var items = $("#myFriendsList li.list-group-item");
				items.each(function(i,item){
					if($(item).find("h5").text() == fromUserName){
						$(this).css("background-color","yellow");
					}
				});
				return;
			}else if($.inArray(fromUserName,names)>=0 && booleanClickFriendList(fromUserName)){//如果点开了好友列表中对应的好友
				putFriendMsgs(msg,getDate());
			}else{
				//在聊天列表中的消息提醒
				var Items = $("#myChatList li.list-group-item");
				Items.each(function(i,item){
					if($(item).find("h5").text() == fromUserName){
						$(this).css("background-color","yellow");
					}
				});
			}
			names = [];
		}else if(msgDate.addFlagMsg == "agree"){
			//如果好友同意了请求就会发一个消息过来
			//收到消息后，重新加载好友列表
			//清空好友列表，相当于刷新
			$("#myFriendsList").empty();
			getMyFriends();
		}else{
			var requestUserName = msgDate.requestUserName;
			var addMsg = msgDate.addMsg;
			createAddInfo(requestUserName,addMsg);
		}
		
	}
}

//判断是否点开好友列表中的好友
function booleanClickFriendList(friendName){
	if($("li.open-chat").find("h5").text() == friendName){
		return true;
	}
	return false;
}

//调出聊天面板与聊天记录,单击调出聊天面板
function getChatBodyAndMsg(){
	$("#chatSidebarBody").on('click','.list-group-item',function(e){
		var ele = $(e.target);
		while(!ele.is('li')){
			ele = ele.parent();
		}
		
		//重置选中状态
		$('li').removeClass('open-chat');
		
		$(ele).addClass('open-chat');
		
		//清除未读消息状态
		$(ele).css("background-color","white");
		
		var result = $(ele[0].children[1].children[0]);

		//获取聊天记录
		getChatMessage(result.html());
		
		
	});
}

//查找聊天记录
function getChatMessage(friendName){
	$.ajax({
		type:'POST',
		url:'../chat/getMsg',
		data:{
			'friendName':friendName,
		},
		dataType:'json',
		async:true,
		success:function(data){
			var userId = data.ids[0].userId;
			var friendId = data.ids[0].friendId;
			//先清空聊天面板
			$("#chatBody").empty();
			
			for(var i=0;i<data.msgs.length;i++){
				(function(i){
					//如果是朋友发来的消息
					if(data.msgs[i].userId == userId && data.msgs[i].friendId == friendId){
						var msg = data.msgs[i].userMsg;
						var time = data.msgs[i].userMsgDate;
						putMyChatMsg(msg,time);
					}else{
						var msg = data.msgs[i].userMsg;
						var time = data.msgs[i].userMsgDate;
						putFriendMsgs(msg,time);
					}
					//设置为可以滚动
					$(".chat-body").css('overflow-y','auto');
				})(i)
			}
			putScroll();
		}
	});
}

//将聊天框中的滚轮置于最下方
function putScroll(){
	var end_msg = document.getElementsByClassName('message-item');
	if(end_msg.length<=0){
		return false;
	}
	end_msg[end_msg.length-1].scrollIntoView();
}

//将好友的消息放在聊天表里
function putFriendMsgs(msg,time){
	$("#chatBody").append(
		$("<div>",{
			'class':'message-item'
		}).append(
			$("<div>",{
				'class':'message-content',
				'html':msg
			}),
			$("<div>",{
				'class':'message-action',
				'html':time
			})
		)
	)
	putScroll();
}

//将发送的消息摆在聊天记录里
function putMyChatMsg(msg,time){
	$("#chatBody").append(
		$("<div>",{
			'class':'message-item outgoing-message'
		}).append(
			$("<div>",{
				'class':'message-content',
				'html':msg
			}),
			$("<div>",{
				'class':'message-action',
				'html':time
			})
		)
	)
	putScroll();
}

//时间工具类
function getDate(){
	var date = new Date();
	var year = date.getFullYear();  //年
	var month = date.getMonth()+1;  //月
	var day = date.getDate();  //日
	
	var hours = date.getHours();  //时
	var min = date.getMinutes();  //分
	var sec = date.getSeconds();  //秒
	
	return year+'-'+month+'-'+day+' '+hours+':'+min+':'+sec;
	
}












/**========================rest风格的添加好友操作==========================**/
function addFriends(){
	$("#addFriendsBtn").on('click',function(){
		if($("#emails").val() == ""){
			alert("请填入用户名");
			return;
		}else if($("#message").val().length > 30){
			alert("请输入30个字符以内的内容");
			$("#message").val("");
		}else{
			$.ajax({
				type:"POST",
				url:'../friends/friends/'+$("#emails").val(),
				data:{
					addMsg:$("#message").val()
				},
				async:true,
				success:function(data){
					console.log(data);
					if(data.flag == "success"){
						alert("请求发送成功");
						$("#emails").val("");
						$("#message").val("");
						$("#addFriends").modal("hide");
					}else{
						alert(data.msg);
						$("#emails").val("");
						$("#message").val("");
					}
				}
			});
		}
	});
}

//添加好友的点击事件
function addFriendsBtnEvents(){
	$(".addFriendsBtn").on("click",function(){
		//清空聊天面板
		$("#chatBody").empty();
		$("#myChatList").children().removeClass("open-chat");
	});
}

//获取添加好友的消息
function getAddFriendsInfo(){
	$.ajax({
		url:'../friends/friends',
		type:'GET',
		async:true,
		success:function(data){
			if(data[0].flag == 'noAddInfo'){
				return;
			}
			for(var i=0;i<data.length;i++){
				(function(i){
					createAddInfo(data[i].requestUserName,data[i].addMsg);
				})(i)
			}
		}
	});
}

//为好友请求绑定事件，弹出模态框，同意或拒绝
function addInfoEvents(){
	$(".users-list").on('click','.list-group-item',function(e){
		var ele = $(e.target);
		while(!ele.is('li')){
			ele = ele.parent();
		}
		//打开模态框
		$("#friendsRequestInfo").modal('show');
		$("#requestUserName").html(ele.find("h5").html());
		$("#addFriendMessage").html(ele.find("p").html());
	});
}

//添加验证消息
function createAddInfo(requestUserName,addMsg){
	$(".users-list").append(
		$("<li>",{
			'class':'list-group-item userRequestList'
		}).append(
			$("<div>",{
				'class':'users-list-body'
			}).append(
				$("<h5>",{
					'html':requestUserName,
					'class':'requestUserNameToMe'
				}),
				$("<p>",{
					'html':addMsg
				})
			)
		)
	)
}

//同意或不同意好友请求的事件
function agreeOrNotFriendsAdd(){
	$("#agreeFriendsBtn").on('click',function(){
		//同意好友请求
		$.ajax({
			type:'post',
			url:'../friends/friends/'+$('#requestUserName').html(),
			async:true,
			data:{
				_method:'put',
				addMsg:$("#addFriendMessage").html()
			},
			success:function(data){
				//同意之后添加到好友列表中，
				//弹出提示框，添加成功
				alert(data.msg);
				//关闭模态框
				$("#friendsRequestInfo").modal('hide');
				
				//删除请求列表中的用户
				$(".requestUserNameToMe").each(function(index,e){
					if($(e).html()==$('#requestUserName').html()){
						//移除请求列表中的信息
						$(e).parent().parent().remove();
						
						//清空好友列表，相当于刷新
						$("#myFriendsList").empty();
						//重新获取好友列表
						getMyFriends();
					}
				});
				
			}
		});
	});
	
	$("#noAgreeFriendsBtn").on('click',function(){
		//拒绝好友请求
		$.ajax({
			type:'post',
			url:'../friends/friends/'+$('#requestUserName').html(),
			async:true,
			data:{
				_method:'delete',
				addMsg:$("#addFriendMessage").html()
			},
			success:function(data){
				alert(data.msg);
				//关闭模态框
				$("#friendsRequestInfo").modal('hide');
				//删除请求列表中的用户
				$(".requestUserNameToMe").each(function(index,e){
					if($(e).html()==$('#requestUserName').html()){
						//移除请求列表中的信息
						$(e).parent().parent().remove();
					}
				});
			}
		});
	});
	
	
}

agreeOrNotFriendsAdd();