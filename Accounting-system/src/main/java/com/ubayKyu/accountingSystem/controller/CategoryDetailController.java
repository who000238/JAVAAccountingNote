package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.repository.LoginRepository;
import com.ubayKyu.accountingSystem.service.CategoryService;

@Controller
public class CategoryDetailController {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CategoryService categoryService;

	// 顯示
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
				String categorybody = categoryRepository.getCategoryDetailBody(CategoryID);
				model.addAttribute("Title", categoryname);
				model.addAttribute("Inner", categorybody);
			} else {
				model.addAttribute("Title", "輸入標題");
				model.addAttribute("Inner", "輸入內容");
			}

		}

		return "CategoryDetail";
	}

	// 動作
	@PostMapping(value = "/CategoryDetail")
	public String CategoryDetail(
			@RequestParam(value = "CategoryID", required = false) String CategoryID,
			@RequestParam("Title") String Title,
			@RequestParam("Inner") String Inner, 
			HttpServletRequest request,
			RedirectAttributes redirAttrs) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		// 變數區
		String categoryID = CategoryID;
		String userid = request.getSession().getAttribute("userid").toString();
		String categoryname = Title;
		String categorybody = Inner;
		String url = "";
		//檢查標題長度
		if (categoryname.length() > 20) {
			//標題超過20字元提示訊息
			redirAttrs.addFlashAttribute("message", "標題長度不得超過20個字元");
			if(categoryID != null) {
				//修改模式下導頁至原先之URL
				url = "?CategoryID=" + CategoryID;
				return "redirect:/CategoryDetail" + url;
			}
			//新增模式下導頁至新增的網址
			return "redirect:/CategoryDetail";
		}
		//用QueryString中的分類ID去搜尋該分類是否存在
		if (categoryService.IsCategoryExistbyCateID(categoryID)) {
			//存在則進入編輯
			//檢查有無分類重複名稱之問題
			if(categoryService.IsCategoryExistbyCateIDAndName(userid, categoryname)) {
				redirAttrs.addFlashAttribute("message", "分類名稱不可重複");
				url = "?CategoryID=" + CategoryID;
				return "redirect:/CategoryDetail" + url;
			}
			//編輯
			categoryRepository.updateCate(categoryname, categorybody, categoryID);
			redirAttrs.addFlashAttribute("message", "編輯成功");
			url = "?CategoryID=" + CategoryID;
			//編輯後導頁至原本頁面並顯示編輯成功訊息
			return "redirect:/CategoryDetail" + url;
		} else {
			// 不存在則進入新增
			//檢查新增的分類有無重複名稱的問題
			if(categoryService.IsCategoryExistbyCateIDAndName(userid, categoryname)) {
				redirAttrs.addFlashAttribute("message", "分類名稱不可重複");
				return "redirect:/CategoryDetail";
			}else {
				//無重複問題則新增份類
				categoryRepository.CreateNewCategory(categoryname, userid, categorybody);
				redirAttrs.addFlashAttribute("message", "新增成功");
			}
			
			// 新增分類後導頁至分類列表頁
			return "redirect:/CategoryList";
		}

	}

}
