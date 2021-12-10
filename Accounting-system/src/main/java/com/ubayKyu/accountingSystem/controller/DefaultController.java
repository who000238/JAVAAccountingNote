package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ubayKyu.accountingSystem.entity.DefaultPageModel;
import com.ubayKyu.accountingSystem.repository.DefaultRepository;

@Controller
public class DefaultController {

	@Autowired
	DefaultRepository defaultRepository;
	
	@GetMapping("/Default")
	public String Default(Model model) {
//		List<DefaultPageModel> list = defaultRepository.getFindAll();
		model.addAttribute("firstRecord", defaultRepository.firstRecord() );
		model.addAttribute("lastRecord", defaultRepository.lastRecord());
		model.addAttribute("recordCount", defaultRepository.recordCount());
		model.addAttribute("memberCount", defaultRepository.memberCount());
		return "Default";
	}

//	@Autowired
//	DefaultPageRepository defaultPageRepository;
//
//	@GetMapping("/Default")
//	public String Default(Model model) {
//		model.addAttribute("firstRecord", defaultPageRepository.newest());
//		model.addAttribute("lastRecord", defaultPageRepository.latest());
//		model.addAttribute("recordCount", "123");
//		model.addAttribute("memberCount", "123");
//		return "Default";
//	}
}
