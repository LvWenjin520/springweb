package org.lwj.app.login.entity;

import java.util.Date;
import java.util.List;

import org.lwj.app.system.authority.entity.Authority;

/***
 * 
 * @author LvWenJin
 *	实体类
 *	用户信息
 */
public class User {
	private int id;
	private String userName;
	private String passWord;
	private Date signupTime;
	private Date loginTime;
	private String userImg;
	
	//用户的角色
	private List<Authority> authority;

	
	public User(int id, String userName, String passWord, Date signupTime, Date loginTime, String userImg,
			List<Authority> authority) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.signupTime = signupTime;
		this.loginTime = loginTime;
		this.userImg = userImg;
		this.authority = authority;
	}


	public List<Authority> getAuthority() {
		return authority;
	}


	public void setAuthority(List<Authority> authority) {
		this.authority = authority;
	}


	public User(String userName, String passWord, Date signupTime, Date loginTime, String userImg,
			List<Authority> authority) {
		this.userName = userName;
		this.passWord = passWord;
		this.signupTime = signupTime;
		this.loginTime = loginTime;
		this.userImg = userImg;
		this.authority = authority;
	}


	public User() {
	}
	
	
	public User(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public User(int id, String userName, String passWord, Date signupTime, Date loginTime, String userImg) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.signupTime = signupTime;
		this.loginTime = loginTime;
		this.userImg = userImg;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Date getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	
	
}
