package org.lwj.app.article.eneity;

//评论的实体类
public class Comment {
	private int commentsId;
	private int articleId;
	private int fromUserId;
	private int targetUserId;
	private String comment;
	private int isArticleComment;
	private String fromUserName;
	private String targetUserName;
	
	public String getFromUserName() {
		return fromUserName;
	}


	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}


	public String getTargetUserName() {
		return targetUserName;
	}


	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}


	public Comment(int articleId, int fromUserId, int targetUserId, String comment, int isArticleComment,
			String fromUserName, String targetUserName) {
		this.articleId = articleId;
		this.fromUserId = fromUserId;
		this.targetUserId = targetUserId;
		this.comment = comment;
		this.isArticleComment = isArticleComment;
		this.fromUserName = fromUserName;
		this.targetUserName = targetUserName;
	}


	public Comment(int commentsId, int articleId, int fromUserId, int targetUserId, String comment,
			int isArticleComment, String fromUserName, String targetUserName) {
		this.commentsId = commentsId;
		this.articleId = articleId;
		this.fromUserId = fromUserId;
		this.targetUserId = targetUserId;
		this.comment = comment;
		this.isArticleComment = isArticleComment;
		this.fromUserName = fromUserName;
		this.targetUserName = targetUserName;
	}


	public Comment() {
	}
	
	
	public Comment(int articleId, int fromUserId, int targetUserId, String comment, int isArticleComment) {
		this.articleId = articleId;
		this.fromUserId = fromUserId;
		this.targetUserId = targetUserId;
		this.comment = comment;
		this.isArticleComment = isArticleComment;
	}
	public Comment(int commentsId, int articleId, int fromUserId, int targetUserId, String comment, int isArticleComment) {
		this.commentsId = commentsId;
		this.articleId = articleId;
		this.fromUserId = fromUserId;
		this.targetUserId = targetUserId;
		this.comment = comment;
		this.isArticleComment = isArticleComment;
	}
	public int getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(int commentsId) {
		this.commentsId = commentsId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public int getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getisArticleComment() {
		return isArticleComment;
	}
	public void setisArticleComment(int isArticleComment) {
		this.isArticleComment = isArticleComment;
	}
	
	
	
}
