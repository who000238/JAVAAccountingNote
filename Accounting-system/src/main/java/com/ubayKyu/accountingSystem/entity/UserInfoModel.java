package com.ubayKyu.accountingSystem.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserInfo")
public class UserInfoModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ID;

	public String Account; 

	//@Column(name="NAME", length=50, nullable=false, unique=false)
	public String Name;
	//@Column(name="EMAIL", length=50, nullable=false, unique=false)
	public String Email;
	//@Column(name="USER_LEVEL", length=50, nullable=false, unique=false)
	public Integer USERLEVEL;
	//@Column(name="CREATE_DATE", length=50, nullable=false, unique=false)
	public String CREATEDATE;
	public String EDITDATE;

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Integer getUSERLEVEL() {
		return USERLEVEL;
	}
	public void setUSERLEVEL(Integer uSERLEVEL) {
		USERLEVEL = uSERLEVEL;
	}
	public String getCREATEDATE() {
		return CREATEDATE;
	}
	public void setCREATEDATE(String cREATEDATE) {
		CREATEDATE = cREATEDATE;
	}
	
	public String getEDITDATE() {
		return EDITDATE;
	}
	public void setEDITDATE(String eDITDATE) {
		EDITDATE = eDITDATE;
	}
	
	
}
