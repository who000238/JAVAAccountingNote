package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CategoryList")
public class CategoryModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ID;

	@Column(name = "categoryname")
	public String categoryname;
	@Column(name = "createtime")
	public LocalDateTime createtime;
	public Integer Count;
	@Column(name = "userid")
	public  String userid;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public LocalDateTime getCreatetime() {
		return createtime;
	}
	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}
	public Integer getCount() {
		return Count;
	}
	public void setCount(Integer count) {
		Count = count;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	

}
