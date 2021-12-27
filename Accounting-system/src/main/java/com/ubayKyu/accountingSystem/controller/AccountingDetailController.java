package com.ubayKyu.accountingSystem.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	//顯示
	@GetMapping("/AccountingDetail")
	public String AccountingDetail(Model model, @RequestParam(value = "AccountingID", required = false) String AccountingID,HttpServletRequest request) {
		//登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		
//		} else {
//			return "AccountingDetail";
//		}
		// 檢查是否帶有QueryString
		if(AccountingID != "") {
			// 檢查該QueryString 是否在DB內有對應資料
			if(accountingRepository.getAccountingDetail(AccountingID).size()>0) 
			{
				Optional<AccountingModel> AccountingModel = accountingService.getAccountingByID(AccountingID);
				model.addAttribute("Amount",AccountingModel.get().getAmount());
				model.addAttribute("Caption", AccountingModel.get().getCaption());
				model.addAttribute("Body", AccountingModel.get().getBody());
				model.addAttribute("Category", AccountingModel.get().getCategory());
				model.addAttribute("Datetime", AccountingModel.get().getCreatedate());
				model.addAttribute("ActType", AccountingModel.get().getActtype());
			}
			//取得登入使用者ID
			String userid = request.getSession().getAttribute("userid").toString();
			List<CategoryModel> categoryList = categoryRepository.getCategoryDetailByUserID(userid);
			model.addAttribute("CategoryList", categoryList);
		}
		return "AccountingDetail";
	}
}
