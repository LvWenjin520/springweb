$(function(){
	
	//md输入框的
	var testEditor;
	
	//标题文字限制
	titleTextLimit();
	
	//获取并展示所有文章
	getAllArticle();
	
	
	
});


//查询所有文章
function getAllArticle(){
	$.ajax({
		url:decodeURI("../../article/articles/"+$(".courseName").text()),
		type:"GET",
		success:function(data){
			for(var i=0;i<data.length;i++){
				//放置文章并解析
				putArticles(data[i],i);
				//阅读更多
				readmoreOrNot();
			}
			openComments();
		}
	});
}

//放置文章的地方
function putArticles(data,mdIndex){
	$(".articles").append(
		$("<div>",{
			"class":"article container-fluid"
		}).append(
			$("<div>",{
				"class":"row article_header"
			}).append(
				$("<div>",{
					"class":"col-md-7 article_title text-center"
				}).append(
					$("<p>",{
						"html":data.articleTitle
					})
				),
				$("<div>",{
					"class":"col-md-5 article_user text-center"
				}).append(
					$("<p>",{
						"html":"--"
					}).append(
						$("<span>",{
							"html":data.userName
						})
					)
				)
			),
			$("<div>",{
				"class":"article_text"
			}).append(
				$("<div>",{
					"id":"showMd"+mdIndex
				}).append(
					$("<textarea>",{
						"style":"display: none;",
						"html":data.article
					})
				)	
			),
		    $("<div>",{
		    	"class":"row article_giveLike"
		    }).append(
		    	$("<div>",{
		    		"class":"col-md-1"
		    	}),
		    	$("<div>",{
		    		"class":"col-md-2"
		    	}).append(
		    		$("<button>",{
		    			"type":"button",
		    			"class":"btn btn-primary agree_btn",
		    			"html":data.agreeMents+"个赞同"
		    		})
		    	),
		    	$("<div>",{
		    		"class":"col-md-2 text-center article_comments"
		    	}).append(
		    		$("<a>",{
		    			"class":"lookComments",
		    			"html":"评论"  //后期要获取评论数量
		    		})
		    	),
		    	$("<div>",{
		    		"class":"col-md-2 text-center"
		    	}).append(
		    		$("<button>",{
		    			"type":"button",
		    			"class":"btn btn-primary agree_btn",
		    			"html":"问答",
		    			"data-toggle":"modal",
		    			"data-target":"#myModal"
		    		})
		    	)
		    ),
			$("<div>",{
				"class":"article_comments_contain"
			}).append(
				$("<div>",{
					"class":"comments_contain_title"
				}).append(
					$("<p>",{
						"class":"comments_contain_title_text",
					})
				),
				$("<div>",{
					"class":"comments_contain_comments"
				}).append(
					$("<div>",{
						"class":"comments_list"
					})
				)
			)
		)
	)
	//解析文章
	showMd("showMd"+mdIndex);
}

//解析文章
function showMd(id){
	var showEditor;
	//解析并显示
	showEditor = editormd.markdownToHTML(id);
}

//打开md输入框
function openMdEditor(divId){
	//打开md输入框
	testEditor = editormd({
    	id:divId,//注意：这里是上面DIV的id
    	width:"100%",
    	height:400,
    	syncScrolling: "single",
    	path:"../../asset/plugs/mdEditor/lib/",
    	emoji : true,
    });
}

//展开与收起按钮
function readmoreOrNot(){
	$('.article_text').readmore({
		speed:500,
		maxHeight: 400,
		moreLink:'<a HREF="#" style="width: 100px;z-index:2;" class="moreLink">展开详情<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></ a>',
		lessLink:'<a HREF="#" style="width: 100px;z-index:2;"  class="lessLink">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></ a>'	
	});
	//隐藏不必要的展开详情按钮
	if($('.article_text p').height()<=100){
		$(this).find(".moreLink").hide();
	}
}

//限制标题文字字数
function titleTextLimit(){
	$(".article_title").each(function(){ 
		var title = $(this).text();
		var len=$(this).text().length;   
		//当前HTML对象text的长度       
		if(len>26){           
			var str="";           
			str=$(this).text().substring(0,26)+".....";  
			//使用字符串截取，获取前30个字符，多余的字符使用“......”代替           
			$(this).html(str);                   
			//将替换的值赋值给当前对象    
			$(this).css({
				"font-size":"25px",
				"margin-bottom":"10px"
				});
			$(this).attr("title",title);
			}    
		});
}

//打开收起评论功能
function openComments(){
	
	$('.lookComments').on('click',function(){
		//获取评论框就是article_comments_contain
		var comments = $($(this).parent().parent().next());
		//如果评论框没有打开，那点击后就去获取评论
		if(comments.height()==0){
			
			//获取评论并添加到页面中
			getComments(comments.parent().find(".article_title p").text(),comments.find(".comments_list"));
			
			//评论框整体的样式
			$(comments).css({
				//"height":"100px",
				"border":"1px solid #DCDCDC"
			});
			
			//评论框标题内容
			var comments_title = comments.find(".comments_contain_title");
			
			//打开评论框后，评论标题的样式
			comments_title.css({
				"height":"50px",
				"background":"#E7E7E7",
			});
			
			//评论框的标题
			comments_title.find(".comments_contain_title_text").text("评论");
			
			
			//收起评论
			$(this).text("收起评论");
			
		}else{
			
			//评论框整体的样式
			comments.css({
				"border":"none"
			});
			
			//评论框标题内容
			var comments_title = comments.find(".comments_contain_title");
			
			//打开评论框后，评论标题的样式
			comments_title.css({
				"height":"0",
				"background":"none",
			});
			
			
			//评论框的标题
			var title_text = comments_title.find(".comments_contain_title_text").text();
			
			comments_title.find(".comments_contain_title_text").text("");
		
			//收起评论
			$(this).text(title_text);
			
			//移除评论
			var ele = $(comments.find(".comments_list"));
			ele.empty();
		}
	});
}

//获取评论的方法
function getComments(articleTitle,currElem){
	$.ajax({
		type:"get",
		url:decodeURI("../../article/comments/"+articleTitle),
		success:function(data){
			
			if(data==""){
				return false;
			}
			
			
			for(var i= 0;i<data.length;i++){
				//在html中添加标签
				appendComments(currElem,data[i]);
			}
			
			//打开文章评论的编辑器
			openArticleCommentEdit(currElem);
		}
	})
}

//添加评论的方法
function appendComments(currElem,data){
	if(data.isArticleComment == 0){  //如果是评论的话
		currElem.append(
				$("<div>",{
					"class":"userComment"
				}).append(
						$("<a>",{
							"html":data.fromUserName
						}),
						$("<p>",{
							"html":data.comment
						}),
						$("<div>",{
							"class":"options",
							"html":"赞    回复"
						})
					)
			)
	}else if(data.isArticleComment == 1){  //讨论的回复
		currElem.append(
				$("<div>",{
					"class":"userComment"
				}).append(
						$("<a>",{
							"html":data.fromUserName
						}),
						$("<span>",{
							"class":"strHuiFu",
							"html":"回复"
						}),
						$("<a>",{
							"html":data.targetUserName
						}),
						$("<p>",{
							"html":data.comment
						}),
						$("<div>",{
							"class":"options",
							"html":"赞    回复"
						})
					)
			)
	}
}

//打开文章评论的编辑器，不是好友间回复的编辑器
function openArticleCommentEdit(currElem){
	//添加编辑器
	currElem.append(
		$("<div>",{
			"id":"articleCommentEditMd"
		}).append(
			$("<textarea>",{
				"style":"display: none;",
			})
		),
		$("<button>",{
			"type":"button",
			"class":"btn btn-primary submit_btn",
			"html":"评论此文章"
		})	
	);
	testEditor = editormd({
		id:"articleCommentEditMd",//注意：这里是上面DIV的id
		width:"100%",
		height:600,
		syncScrolling: "single",
		path:"../../asset/plugs/mdEditor/lib/",
		emoji : true,
	});
	
}
