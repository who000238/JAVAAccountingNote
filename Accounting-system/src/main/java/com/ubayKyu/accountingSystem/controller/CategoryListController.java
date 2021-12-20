package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.dto.CategoryInterface;
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

			List<CategoryInterface> list = categoryRepository.getFindAll(userid);
			model.addAttribute("list", list);			
			return "CategoryList";
		}

	}


	@RequestMapping(value = "/CategoryList", method = RequestMethod.POST)
	public String CategoryListDel(@RequestParam(value = "cblDel", required = false) String[] categoryDel,
			@RequestParam(value = "btnAdd", required = false) String btnAdd, HttpServletRequest request,
			RedirectAttributes redirAttrs) {
	
		String temp = btnAdd;
		if (temp != null) {
			return "CategoryDetail";
		}
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			// 確認有無清單選取
			if (categoryDel != null) {
				for (String categoryid : categoryDel) // 用for跑字串陣列內的數值
				{
					// 檢查選中分類內有無紀錄
					int count = categoryRepository.checkCateCount(categoryid);
					if(count == 0) {
						
						categoryRepository.deleteCat(categoryid);
						redirAttrs.addFlashAttribute("message", "刪除成功");
					
					}
					else {
						redirAttrs.addFlashAttribute("message", "刪除失敗");
						
					}
				}
			} 
		}
		
		return "redirect:/CategoryList";
	}

}
