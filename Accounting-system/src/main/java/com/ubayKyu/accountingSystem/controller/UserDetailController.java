package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.entity.UserInfoModel;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Controller
public class UserDetailController {
	@Autowired
	UserInfoRepository userInfoRepository;
	//顯示
	@GetMapping("/UserDetail")
	public String UserDetail(
			Model model,
			@RequestParam(value = "UserID", required = false) String UserID,
			HttpServletRequest request,
			RedirectAttributes redirAttrs) {
		Object Logined = request.getSession().getAttribute("loginLevel");
		
		if (Logined == null) {
			return "Login";
		} 
		//若帶有QueryString
		if(UserID != null) {
			
			List<UserInfoModel> list = userInfoRepository.getUserInfoByID(UserID);
			//確認該UserID在DB中有相對應資料
			if(list.size()>0) {
				model.addAttribute("Account", list.get(0).getAccount());
				model.addAttribute("Name", list.get(0).getName());
				model.addAttribute("Mail", list.get(0).getEmail());
				model.addAttribute("UserLevel", list.get(0).getUSERLEVEL());
				model.addAttribute("CreateDate", list.get(0).getCREATEDATE());		
				model.addAttribute("EditDate", list.get(0).getEDITDATE());
			}else {
				redirAttrs.addFlashAttribute("message", "查無該使用者");
				return "redirect:/UserList";
			}
			
		}
//		else {
//			return "UserDetail";
//		}
		return "UserDetail";
	}
}
