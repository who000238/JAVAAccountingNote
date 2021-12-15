package com.ubayKyu.accountingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.LoginPageModel;

public interface LoginRepository  extends JpaRepository<LoginPageModel, String>{
	@Query(value = "SELECT * FROM UserInfo WHERE Account = ?1", nativeQuery = true)
	public LoginPageModel doLogin(String Account);
}
