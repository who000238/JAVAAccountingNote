package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryDetailController {
	@GetMapping("/CategoryDetail")
	public String CategoryDetail() {
		return "CategoryDetail";
	}
}
