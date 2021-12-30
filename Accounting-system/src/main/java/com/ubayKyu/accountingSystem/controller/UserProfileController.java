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

import com.ubayKyu.accountingSystem.entity.UserInfoModel;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Controller
public class UserProfileController {
	@Autowired
	UserInfoRepository userInfoRepository;

	// 顯示
	@GetMapping("/UserProfile")
	public String UserProfile(Model model,HttpServletRequest request) {
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} 
//		else {
//			return "UserProfile";
//		}
		//取得使用者ID
		String userid = request.getSession().getAttribute("userid").toString();
		List<UserInfoModel> list = userInfoRepository.getUserInfoByID(userid);
//		Optional<UserInfoModel> UserInfoModel = userInfoService.getUserInfoByID(userid);
		model.addAttribute("Account",list.get(0).getAccount());
		model.addAttribute("Name", list.get(0).getName());
		model.addAttribute("Mail", list.get(0).getEmail());
		return "UserProfile";
	}
	// 動作
	@PostMapping(value = "/UserProfile")
	public String UserProfile(
			@RequestParam("Name") String Name,
			@RequestParam("Mail") String Mail,
			RedirectAttributes redirAttrs,
			HttpServletRequest request) {
		//取得使用者ID
		String userid = request.getSession().getAttribute("userid").toString();
		if(Name.length()>20) {
			redirAttrs.addFlashAttribute("message", "姓名請勿超過20字元");			
		}
		else if(Mail.length()>100) {
			redirAttrs.addFlashAttribute("message", "電子信箱總計請勿超過100字元");
		} else {
			userInfoRepository.UpdateUserInfo(Name, Mail, userid);
			redirAttrs.addFlashAttribute("message", "更新成功");
		}
		
		return "redirect:/UserProfile";
	}
}
