package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDetailController {
	@GetMapping("/UserDetail")
	public String UserDetail() {
		return "UserDetail";
	}
}
