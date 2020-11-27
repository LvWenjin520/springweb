package org.lwj.app.article.dao;

import java.util.List;

import org.lwj.app.article.eneity.Article;
import org.lwj.app.article.eneity.Comment;
import org.springframework.stereotype.Repository;

@Repository("articleDao")
public interface ArticleDao {
	boolean insertUserArticle(Article article);
	
	int queryArticleTitleCount(Article article);
	
	
	//查询此课题下的所有文章
	List<Article> queryArticlesByCourseId(int courseId);
	
	//查询文章下的所有评论
	List<Comment> queryCommentsByArticleId(int articleId);
	
	//根据文章标题查询文章id
	int queryArticleIdByArticleTitle(String articleTitle);
}
