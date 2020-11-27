package org.lwj.app.article.service.impl;

import java.util.List;

import org.lwj.app.article.dao.ArticleDao;
import org.lwj.app.article.eneity.Article;
import org.lwj.app.article.eneity.Comment;
import org.lwj.app.article.service.ArticleService;
import org.lwj.app.course.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	@Qualifier("articleDao")
	ArticleDao articleDao;
	
	@Autowired
	@Qualifier("courseDao")
	CourseDao courseDao;
	
	@Override
	@Transactional
	public int insertUserArticle(Article article) {
		//判断是否重复
		int isExit = articleDao.queryArticleTitleCount(article);
		
		if(isExit > 0) {
			return -1;  //标题重复
		}else if(isExit == 0) {
			articleDao.insertUserArticle(article);
			return 1;  //成功
		}
			return 0;  //系统异常
	}

	@Override
	public List<Article> queryArticlesByCourseId(int courseId) {
		List<Article> articles = articleDao.queryArticlesByCourseId(courseId);
		return articles;
	}

	
	//查询所有的文章
	@Override
	public List<Article> getArticlesByCourseId(String courseName) {
		
		int courseId = courseDao.queryCourseIdByCourseName(courseName);
		if(courseId>0) {
			return articleDao.queryArticlesByCourseId(courseId);
		}
		
		return null;
	}

	
	//根据标题查询所有的评论
	@Override
	public List<Comment> queryCommentsByArticleId(String articleTitle) {
		
		int articleId = articleDao.queryArticleIdByArticleTitle(articleTitle);
		List<Comment> comments = articleDao.queryCommentsByArticleId(articleId);
		
		return comments;
	}
	
	
}
