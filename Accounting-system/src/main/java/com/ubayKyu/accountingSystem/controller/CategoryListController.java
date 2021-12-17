package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.entity.CategoryModel;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;

@Controller
public class CategoryListController {
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/CategoryList")
	public String CategoryList(Model model, HttpServletRequest request) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			// 取得登入使用者的ID去DB內尋找該使用者的自定義分類
			String userid = request.getSession().getAttribute("userid").toString();

			List<CategoryModel> list = categoryRepository.getFindAll(userid);
			model.addAttribute("list", list);
			return "CategoryList";
		}

	}
	@PostMapping
	@RequestMapping(value = "/CategoryList", method = RequestMethod.POST)
	public String CategoryListDel(@RequestParam(value = "cblDel", required = false) String[] categoryDel,
			@RequestParam(value = "btnAdd", required = false) String btnAdd,
			HttpServletRequest request) {
		String temp = btnAdd;
		if(temp != "") {
			return "CategoryDetail";
		}
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			// 確認有無清單選取
			if (categoryDel != null) {
				for (String categoryid : categoryDel) //用for跑字串陣列內的數值
				{
					categoryRepository.deleteCat(categoryid);
					
				}
			} else {

			}
		}
		return "CategoryList";
	}

}
