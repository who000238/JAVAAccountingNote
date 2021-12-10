package com.ubayKyu.accountingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountingListController {
	@GetMapping("/AccountingList")
	public String AccountingList() {
		return "AccountingList";
	}
}
