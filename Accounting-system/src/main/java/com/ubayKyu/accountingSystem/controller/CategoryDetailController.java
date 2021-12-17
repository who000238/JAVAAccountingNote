package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.repository.LoginRepository;

@Controller
public class CategoryDetailController {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/CategoryDetail")
	public String CategoryDetail(Model model, @RequestParam(value = "CategoryID", required = false) String CategoryID,
			HttpServletRequest request) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
//		} else {
//			return "CategoryDetail";
//		}

		// 檢查是否帶有QueryString
		if (CategoryID != "") {
			// 檢查該QueryString 是否在DB內有對應資料
			if (categoryRepository.getCategoryDetail(CategoryID).size() > 0) {
				String categoryname = categoryRepository.getCategoryDetailName(CategoryID);
				model.addAttribute("Title", categoryname);
				model.addAttribute("Inner", categoryname);
			} else {
				model.addAttribute("Title", "輸入");
				model.addAttribute("Inner", "輸入");
			}

		}

		return "CategoryDetail";
	}

	@RequestMapping(value = "/CategoryDetail", method = RequestMethod.POST)
	public String CategoryDetail(@RequestParam("Title") String Title, @RequestParam("Inner") String Inner,
			HttpServletRequest request) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		// 新增分類
		String categoryname = Title;
		String userid = request.getSession().getAttribute("userid").toString();
		categoryRepository.CreateNewCategory(categoryname,userid);
		return "/CategoryList";

	}
//
//	@GetMapping("/CategoryDetail/{id}")
//	public String CategoryDetailEdit() {
//		return "CategoryDetail";
//	}
}
