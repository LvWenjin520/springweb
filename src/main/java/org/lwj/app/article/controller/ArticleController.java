package org.lwj.app.article.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.lwj.app.article.eneity.Article;
import org.lwj.app.article.eneity.Comment;
import org.lwj.app.article.service.ArticleService;
import org.lwj.app.login.entity.User;
import org.lwj.app.login.service.UserService;
import org.lwj.utils.MyStringUtils.StringEncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//文章的控制器
@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	@Qualifier("articleService")
	ArticleService articleService;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	
	//保存用户的文章
	@PostMapping("article")
	@ResponseBody
	public Map<String,String> saveUserArticle(
			HttpSession session,
			@RequestParam("articleTitle") String articleTitle,
			@RequestParam("article") String article
			){
		
		
		
		
		User user = (User) session.getAttribute("user");
		String userName = user.getUserName();
		int userId = userService.queryUserIdByUserName(userName);
		
		Article userArticle = new Article(userId,userName, articleTitle, article,1);
		
		
		
		
		int flag = articleService.insertUserArticle(userArticle);
		
		Map<String,String> result = new HashMap<>();
		if(flag == 1) {
			result.put("flag", "success");
			return result;
		}else if(flag < 0) {
			result.put("flag", "标题重复，请更换一个标题");
			return result;
		}
		result.put("flag", "系统异常，请联系管理员");
		return result;
	}
	
	
	//获取所有的文章
	@GetMapping("articles/{courseName}")
	@ResponseBody
	public List<Article> getAllArticles(
			@PathVariable("courseName") String courseName
			) throws UnsupportedEncodingException{
		
		List<Article> articles = articleService.getArticlesByCourseId(courseName);
		
		//转换编码
		for(int i=0;i<articles.size();i++) {
			articles.get(i).setArticle(
				StringEncodingUtil.stringEnCoding(articles.get(i).getArticle())
			);
		}
		
		//返回文章
		return articles;
	}
	
	
	//获取所有的评论，根据文章的标题
	@GetMapping("comments/{articleTitle}")
	@ResponseBody
	public List<Comment> getCommentsByArticleId(
			@PathVariable("articleTitle") String articleTitle
			) throws UnsupportedEncodingException{
		
		
		List<Comment> comments = articleService.queryCommentsByArticleId(articleTitle);
		
		for(Comment comment:comments) {
			comment.setComment(StringEncodingUtil.stringEnCoding(comment.getComment()));
		}
		
		return comments;
	}
	
	
	
	
	
}
