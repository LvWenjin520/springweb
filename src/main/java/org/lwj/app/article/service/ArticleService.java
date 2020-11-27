package org.lwj.app.article.service;

import java.util.List;

import org.lwj.app.article.eneity.Article;
import org.lwj.app.article.eneity.Comment;

public interface ArticleService {
	int insertUserArticle(Article article);
	
	
	//查询此课题下的所有文章
	List<Article> queryArticlesByCourseId(int courseId);
	
	//获取所有的而文章
	List<Article> getArticlesByCourseId(String courseName);
	
	//查询文章下的所有评论
	List<Comment> queryCommentsByArticleId(String articleTitle);
}
