package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accounting")
public class AccountingModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private String ID;
	//	使用者辨識碼
	@Column(name = "userid")
	private String userid;
	//	標題
	@Column(name = "caption")
	private String caption;
	//	金額
	@Column(name = "amount")
	private Integer amount;
	//	收支種類
	@Column(name = "acttype")
	private Integer acttype;
	//	建立時間
	@Column(name = "createdate")
	private String createdate;
	//	備註
	@Column(name = "body")
	private String body;
	//	分類辨識碼
	@Column(name = "category")
	private String category;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = Math.abs(amount);
	}
	public Integer getActtype() {
		return acttype;
	}
	public void setActtype(Integer acttype) {
		this.acttype = acttype;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
