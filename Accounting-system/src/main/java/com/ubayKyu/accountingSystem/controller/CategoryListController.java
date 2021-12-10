package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryListController {
	@GetMapping("/CategoryList")
	public String CategoryList() {
		return "CategoryList";
	}
}
