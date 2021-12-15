package com.ubayKyu.accountingSystem.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserInfo")
public class DefaultPageModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ID;

	public String Account; 
	//@Column(name="PWD", length=50, nullable=false, unique=false)
	public String PWD;
	//@Column(name="NAME", length=50, nullable=false, unique=false)
	public String Name;
	//@Column(name="EMAIL", length=50, nullable=false, unique=false)
	public String Email;
	//@Column(name="USER_LEVEL", length=50, nullable=false, unique=false)
	public Integer USERLEVEL;
	//@Column(name="CREATE_DATE", length=50, nullable=false, unique=false)
	public Date CREATEDATE;
	public String getId() {
		return ID;
	}
	public void setId(String id) {
		this.ID = id;
	}
	public String getAccount() {
		return Account;
	}
	public void setAccount(String account) {
		Account = account;
	}
	public String getPWD() {
		return PWD;
	}
	public void setPWD(String pWD) {
		PWD = pWD;
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
	public Date getCREATEDATE() {
		return CREATEDATE;
	}
	public void setCREATEDATE(Date cREATEDATE) {
		CREATEDATE = cREATEDATE;
	}


}
