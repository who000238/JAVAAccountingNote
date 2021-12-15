package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ubayKyu.accountingSystem.repository.LoginRepository;

@Controller
public class AccountingListController {

	@Autowired
	LoginRepository loginRepository;
	
	@GetMapping("/AccountingList")
	public String AccountingList(HttpServletRequest request) {
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			return "AccountingList";
		}
		
	}
}
