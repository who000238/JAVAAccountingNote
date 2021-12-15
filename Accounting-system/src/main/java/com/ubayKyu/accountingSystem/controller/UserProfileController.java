package com.ubayKyu.accountingSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

	
	@GetMapping("/UserProfile")
	public String UserProfile(HttpServletRequest request) {
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			return "UserProfile";
		}
		
	}
}
