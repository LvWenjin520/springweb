package org.lwj.app.system.authority.entity;

import org.springframework.stereotype.Component;


//用户权限的实体类
@Component("authority")
public class Authority {
	
	private int id;
	private String role_name;
	private int role_id;
	private String role_description;
	private int role_status; //状态，1表示可用，权限控制用状态来控制
	
	
	public Authority() {
	}

	public Authority(String role_name, int role_id, String role_description, int role_status) {
		this.role_name = role_name;
		this.role_id = role_id;
		this.role_description = role_description;
		this.role_status = role_status;
	}
	
	public Authority(int id, String role_name, int role_id, String role_description, int role_status) {
		this.id = id;
		this.role_name = role_name;
		this.role_id = role_id;
		this.role_description = role_description;
		this.role_status = role_status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_description() {
		return role_description;
	}
	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}
	public int getRole_status() {
		return role_status;
	}
	public void setRole_status(int role_status) {
		this.role_status = role_status;
	}
	
	
}
