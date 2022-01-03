package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class UserDetailController {
	@Autowired
	UserInfoRepository userInfoRepository;

	// 顯示
	@GetMapping("/UserDetail")
	public String UserDetail(Model model, @RequestParam(value = "UserID", required = false) String UserID,
			HttpServletRequest request, RedirectAttributes redirAttrs) {
		// 判斷登入
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		// 判斷使用者等級
		Integer UserLevel = (Integer) request.getSession().getAttribute("loginLevel");
		if (UserLevel != 0) {
			return "redirect:/UserProfile";
		}
		// 若帶有QueryString
		if (UserID != null) {
			// 不讓管理者更改自己帳號的權限
			String QueryID = request.getSession().getAttribute("userid").toString();
			if (UserID.equals(QueryID)) {
				
				model.addAttribute("DDLoff","DDLoff");
			}

			List<UserInfoModel> list = userInfoRepository.getUserInfoByID(UserID);
			// 確認該UserID在DB中有相對應資料
			if (list.size() > 0) {
				model.addAttribute("Account", list.get(0).getAccount());
				model.addAttribute("Name", list.get(0).getName());
				model.addAttribute("Mail", list.get(0).getEmail());
				model.addAttribute("UserLevel", list.get(0).getUSERLEVEL());
				model.addAttribute("CreateDate", list.get(0).getCREATEDATE());
				model.addAttribute("EditDate", list.get(0).getEDITDATE());
			} else {
				redirAttrs.addFlashAttribute("message", "查無該使用者");
				return "redirect:/UserList";
			}

		}
//		else {
//			return "UserDetail";
//		}
		return "UserDetail";
	}

	// 動作
	@PostMapping(value = "/UserDetail")
	public String UserDetail(@RequestParam(value = "UserID", required = false) String UserID,
			@RequestParam(value = "AccInput", required = false) String AccInput, @RequestParam("Name") String Name,
			@RequestParam("Mail") String Mail, @RequestParam("UserLevel") String UserLevel,
			RedirectAttributes redirAttrs, HttpServletRequest request) {
		// 檢查輸入帳號長度
		if (AccInput.length() > 20) {
			redirAttrs.addFlashAttribute("message", "帳號請勿超過20字元");
			return "redirect:/UserDetail";
		}
		// 編輯模式
		if (UserID != null) {
			userInfoRepository.ManagerUpdateUserInfo(Name, Mail, UserLevel, UserID);
			String url = "?UserID=" + UserID;
			redirAttrs.addFlashAttribute("message", "修改成功");
			return "redirect:/UserDetail" + url;
		} else {

			// 檢查新增模式下帳號使否重複
			List<UserInfoModel> list = userInfoRepository.CheckAccExist(AccInput);
			if (list.size() > 0) {
				redirAttrs.addFlashAttribute("message", "帳號重複");
				return "redirect:/UserDetail";
			}
			// 新增會員
			userInfoRepository.CreateUser(AccInput, Name, Mail, UserLevel);
		}
		// 新增後導頁至會員列表頁
		return "redirect:/UserDetail";
	}
}
