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

import com.ubayKyu.accountingSystem.dto.AccountingInterface;
import com.ubayKyu.accountingSystem.repository.AccountingRepository;
import com.ubayKyu.accountingSystem.repository.LoginRepository;
import com.ubayKyu.accountingSystem.service.AccountingService;

@Controller
public class AccountingListController {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	AccountingRepository accountingRepository;
	@Autowired
	AccountingService accountingService;
	//顯示
	@GetMapping("/AccountingList")
	public String AccountingList(Model model,HttpServletRequest request) {
		//登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");
		if (Logined == null) {
			return "Login";
		} else {
			//取得登入使用者的ID去DB內尋找該使用者的流水帳紀錄
			String userid = request.getSession().getAttribute("userid").toString();
			//小計部分
			int Total = accountingService.getTotalAmountByUserid(userid);
			int MonthTotal=accountingService.getThisMonthTotalAmountByUserId(userid);
			model.addAttribute("TotalAmount", Total);	
			model.addAttribute("MountAmount", MonthTotal);	
//			//流水帳部分
			
			List<AccountingInterface> list = accountingRepository.getFindAll(userid);
			model.addAttribute("list", list);
			
	
			return "AccountingList";
		}
	}
	@RequestMapping(value = "/AccountingList", method = RequestMethod.POST)
	public String AccountingListDel(
			@RequestParam(value = "cblDel", required = false) String[] AccountingDel,
			@RequestParam(value = "btnAdd", required = false) String btnAdd,
			HttpServletRequest request,
			RedirectAttributes redirAttrs) {
		//Add按鈕動作
		String temp = btnAdd;
		if (temp != null) {
			return "AccountingDetail";
		}
		// 登入判斷
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		}
		
		//確認有無清單選取
		if(AccountingDel != null) {
			for(String Accountingid : AccountingDel )// 用for跑字串陣列內的數值
			{
				accountingRepository.deleteById(Accountingid);
				redirAttrs.addFlashAttribute("message", "刪除成功");
			}
		}
		return "redirect:/AccountingList";
	}
}
