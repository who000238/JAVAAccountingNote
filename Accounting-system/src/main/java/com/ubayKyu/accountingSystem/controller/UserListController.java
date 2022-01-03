package com.ubayKyu.accountingSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.entity.UserInfoModel;
import com.ubayKyu.accountingSystem.repository.AccountingRepository;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;
import com.ubayKyu.accountingSystem.repository.DeleteLogRepository;
import com.ubayKyu.accountingSystem.repository.UserInfoRepository;

@Controller
public class UserListController {
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	DeleteLogRepository deleteLogRepository;
	@Autowired
	AccountingRepository accountingRepository;
	@Autowired
	CategoryRepository categoryRepository;
	// 顯示

	@GetMapping("/UserList")
	public String UserList(Model model, HttpServletRequest request, RedirectAttributes redirAttrs) {
		//判斷登入
		Object Logined = request.getSession().getAttribute("loginLevel");
		if (Logined == null) {
			return "Login";
		}
		//判斷使用者等級
		Integer UserLevel = (Integer)request.getSession().getAttribute("loginLevel");
		if(UserLevel != 0) {
			return "redirect:/UserProfile";
		}
//		else {
//		return "UserList";
//	}
		// 取得所有使用者清單
		List<UserInfoModel> list = userInfoRepository.getAllUserInfo();
		model.addAttribute("list", list);
		return "/UserList";
	}

	// 動作
	@RequestMapping(value = "/UserList", method = RequestMethod.POST)
	public String UserListDel(@RequestParam(value = "cblDel", required = false) String[] UserDel,
			HttpServletRequest request, RedirectAttributes redirAttrs) {
		//變數區
		//管理員ID
		String userid = request.getSession().getAttribute("userid").toString();
		List<UserInfoModel> ManagerInfo =  userInfoRepository.getUserInfoByID(userid);
		String managerName = ManagerInfo.get(0).getName();
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			// 確認有無清單選取
			if (UserDel != null) {
				for (String DelUserID : UserDel) // 用for跑字串陣列內的數值
				{
					if(DelUserID.equals(userid)) {
						redirAttrs.addFlashAttribute("message", "無法刪除自己");
						return "redirect:/UserList";
					}
					
					List<UserInfoModel> UserInfo = userInfoRepository.getUserInfoByID(DelUserID);
					String userName = UserInfo.get(0).getName();
					//刪除會員資料
					userInfoRepository.DeleteUser(DelUserID);
					//刪除會員流水帳
					accountingRepository.deleteAllAccByuserid(DelUserID);
					//刪除會員自定義分類
					categoryRepository.deleteCatByuserid(DelUserID);
					//日誌寫入
					deleteLogRepository.CreateDeleteLog(userid, managerName, DelUserID, userName);
					redirAttrs.addFlashAttribute("message", "刪除成功");
				}

			}
		}

		return "redirect:/UserList";
	}
}
