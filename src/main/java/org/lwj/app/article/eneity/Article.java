package org.lwj.app.article.eneity;


//文章的实体类
public class Article {
	private int articleId;
	private int userId;
	private String userName;
	private String articleTitle;
	private String article;
	private int courseId;
	private int agreeMents;
	
	
	
	public int getAgreeMents() {
		return agreeMents;
	}

	public void setAgreeMents(int agreeMents) {
		this.agreeMents = agreeMents;
	}

	public Article(int userId, String userName, String articleTitle, String article, int courseId, int agreeMents) {
		this.userId = userId;
		this.userName = userName;
		this.articleTitle = articleTitle;
		this.article = article;
		this.courseId = courseId;
		this.agreeMents = agreeMents;
	}

	public Article(int articleId, int userId, String userName, String articleTitle, String article, int courseId,
			int agreeMents) {
		this.articleId = articleId;
		this.userId = userId;
		this.userName = userName;
		this.articleTitle = articleTitle;
		this.article = article;
		this.courseId = courseId;
		this.agreeMents = agreeMents;
	}

	public Article(int userId, String userName, String articleTitle, String article, int courseId) {
		this.userId = userId;
		this.userName = userName;
		this.articleTitle = articleTitle;
		this.article = article;
		this.courseId = courseId;
	}

	public Article() {
	}
	
	public Article(int userId, String articleTitle, String article) {
		this.userId = userId;
		this.articleTitle = articleTitle;
		this.article = article;
	}

	public Article(int userId, String articleTitle, String article, int courseId) {
		this.userId = userId;
		this.articleTitle = articleTitle;
		this.article = article;
		this.courseId = courseId;
	}

	public Article(int articleId, int userId, String articleTitle, String article, int courseId) {
		this.articleId = articleId;
		this.userId = userId;
		this.articleTitle = articleTitle;
		this.article = article;
		this.courseId = courseId;
	}

	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
}
