package com.ubayKyu.accountingSystem.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.dto.AccountingInterface;
import com.ubayKyu.accountingSystem.entity.AccountingModel;
import com.ubayKyu.accountingSystem.entity.CategoryModel;
import com.ubayKyu.accountingSystem.repository.AccountingRepository;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.repository.LoginRepository;
import com.ubayKyu.accountingSystem.service.AccountingService;

@Controller
public class AccountingDetailController {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	AccountingRepository accountingRepository;
	@Autowired
	AccountingService accountingService;
	@Autowired
	CategoryRepository categoryRepository;

	// 顯示
	@GetMapping("/AccountingDetail")
	public String AccountingDetail(Model model,
			@RequestParam(value = "AccountingID", required = false) String AccountingID, HttpServletRequest request) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}

//		} else {
//			return "AccountingDetail";
//		}
		// 檢查是否帶有QueryString
		if (AccountingID != "") {
			// 檢查該QueryString 是否在DB內有對應資料
			if (accountingRepository.getAccountingDetail(AccountingID).size() > 0) {
				Optional<AccountingModel> AccountingModel = accountingService.getAccountingByID(AccountingID);
				model.addAttribute("Amount", AccountingModel.get().getAmount());
				model.addAttribute("Caption", AccountingModel.get().getCaption());
				model.addAttribute("Body", AccountingModel.get().getBody());
				model.addAttribute("Category", AccountingModel.get().getCategory());
				model.addAttribute("Datetime", AccountingModel.get().getCreatedate());
				model.addAttribute("ActType", AccountingModel.get().getActtype());
			}
			// 取得登入使用者ID
			String userid = request.getSession().getAttribute("userid").toString();
			List<CategoryModel> categoryList = categoryRepository.getCategoryDetailByUserID(userid);
			model.addAttribute("CategoryList", categoryList);
		}
		return "AccountingDetail";
	}

	// 動作
	@PostMapping(value = "/AccountingDetail")
	public String AccountingDetail(@RequestParam(value = "AccountingID", required = false) String AccountingID,
			@RequestParam("ActType") String ActType, @RequestParam("Category") String Category,
			@RequestParam("Amount") String Amount, @RequestParam("Caption") String Caption,
			@RequestParam("Body") String Body, RedirectAttributes redirAttrs, HttpServletRequest request) {
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		// 檢查amount caption body 是否為空值
		if (Caption == "" || Body == "") {
			redirAttrs.addFlashAttribute("message", "請確認所有欄位都有輸入值");
			if (AccountingID != null) {
				String url = "?AccountingID=" + AccountingID;
				return "redirect:/AccountingDetail" + url;
			} else {
				return "redirect:/AccountingDetail";
			}
		}
		// 取得登入使用者ID
		String userid = request.getSession().getAttribute("userid").toString();
		// 檢查QueryString使否帶有分類ID 無則代表新增模式 有則檢查DB內是否有對應資料
		if (AccountingID != null) {
			// 檢查該流水帳ID在DB內是否有資料 有則編輯，無則新增
			List<AccountingInterface> list = accountingRepository.getFindAll(userid);
			if (list.size() > 0) {
				// 編輯模式
				accountingRepository.UpdateAccounting(Caption, Amount, ActType, Body, Category, AccountingID);
				// 編輯成功後導頁至原先頁面並顯示修改成功訊息
				redirAttrs.addFlashAttribute("message", "編輯成功");
				String url = "?AccountingID=" + AccountingID;
				return "redirect:/AccountingDetail" + url;
			} else {
				// 新增模式
				accountingRepository.CreateNewAccounting(userid, Caption, Amount, ActType, Body, Category);
			}
		} else {
			// 新增模式
			accountingRepository.CreateNewAccounting(userid, Caption, Amount, ActType, Body, Category);
		}

		// 新增後導頁至流水帳列表頁
		return "redirect:/AccountingList";
	}
}
