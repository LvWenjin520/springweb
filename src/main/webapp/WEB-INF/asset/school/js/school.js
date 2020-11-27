/**
 * 
 */

$(function(){
	//获取所有的课程
	getCourses();
});


//获取课程并添加到页面上
function getCourses(){
	$.ajax({
		type:"get",
		url:"../course/courses",
		data:{
			pageNo:1  //第一次查询从页码1开始
		},
		success:function(data){
			
			//如果什么都没有就返回
			if(data==""){
				return false;
			}
			
			//摆放课程
			putCourses(data.courses);
			
			//限制标题字数
			limitTitleAndInfo($(".school_cards .cards h1"),12);
			
			//限制介绍字数
			limitTitleAndInfo($(".school_cards .cards p"),30);
		
			//设置分页插件设置项
			var options = {//根据后台返回的分页相关信息，设置插件参数
					bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
					currentPage : data.pageNo, //当前页数
					totalPages : data.totalPage, //总页数
					numberOfPages : 6,//每页记录数
					itemTexts : function(type, page, current) {//设置分页按钮显示字体样式
						switch (type) {
						case "first":
							return "首页";
						case "prev":
							return "上一页";
						case "next":
							return "下一页";
						case "last":
							return "末页";
						case "page":
							return page;
						}
					},
				  onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
					$.ajax({//根据page去后台加载数据
						url : "../course/courses",
						type : "get",
						dataType : "json",
						data : {
							pageNo : page
						},
						success : function(data) {
							//清空页面
							$(".first_row").empty();
							$(".second_row").empty();
							
							//摆放课程
							putCourses(data.courses);
							
							//限制标题字数
							limitTitleAndInfo($(".school_cards .cards h1"),12);
							
							//限制介绍字数
							limitTitleAndInfo($(".school_cards .cards p"),30);
						}
					});
				  }
			    };
		
			//设置分页
			$('#mypage').bootstrapPaginator(options);//设置分页
		
		}
		
	});
}

//将查询到的课程摆放到页面上
function putCourses(courses){
	if(courses.length == 0){
		return;
	}else if(courses.length<=3&&courses.length>0){
		for(var i = 0;i<courses.length;i++){
			(function(i){
				//放在第一行
				putCourse($(".first_row"),courses[i]);
			})(i)
		}
	}else{
		for(var i = 0;i<courses.length;i++){
			if(i<3){
				//放在第一行
				putCourse($(".first_row"),courses[i]);
			}else{
				//放在第二行
				putCourse($(".second_row"),courses[i]);
			}
		}
	}
}

//一个一个放置课程
function putCourse(elem,course){
	
	elem.append(
		$("<div>",{
			"class":"cards col-md-4 text-center"
		}).append(
			$("<div>",{
				"class":"cards_border"
			}).append(
				$("<h1>",{
					"html":course.courseName
				}),
				$("<a>",{
					"href":"#",
					"html":"--"+course.userName
				}),
				$("<p>",{
					"title":course.courseInfo,
					"html":course.courseInfo
				}),
				$("<a>",{
					"href":"../course/course/"+course.courseName
				}).append(
					$("<button>",{
						"type":"button",
						"class":"btn btn-primary toCoursePageBtn",
						"html":"点击进入"
					})
				)
			)	
		)
	);
}

//限制标题与简介的字数
function limitTitleAndInfo(elems,limtiSize){
	elems.each(function(){ 
		var title = $(this).text();
		var len=$(this).text().length;   
		//当前HTML对象text的长度       
		if(len>limtiSize){           
			var str="";           
			str=$(this).text().substring(0,limtiSize)+".....";  
			//使用字符串截取，获取前30个字符，多余的字符使用“......”代替           
			$(this).html(str);                   
			//将替换的值赋值给当前对象    
			//$(this).css("fontSize","18px");
			$(this).attr("title",title);
			}    
	});
}






