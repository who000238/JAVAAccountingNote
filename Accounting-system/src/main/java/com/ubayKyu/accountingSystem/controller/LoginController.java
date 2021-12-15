package com.ubayKyu.accountingSystem.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ubayKyu.accountingSystem.repository.LoginRepository;

@Controller
public class LoginController {

	@Autowired
	LoginRepository loginRepository;

	@GetMapping("/Login")
	public String Login(HttpServletRequest request) {
		Object Logined = request.getSession().getAttribute("loginLevel");

		if (Logined == null) {
			return "Login";
		} else {
			return "UserProfile";
		}
	}

	@PostMapping("/Login")
	public String doLogin(@RequestParam("account") String account, @RequestParam("password") String password,
			Map<String, Object> map, HttpSession session) {
		if(loginRepository.doLogin(account)!=null) {
			String userAcc = loginRepository.doLogin(account).Account;
			String userPwd = loginRepository.doLogin(account).PWD;
			
			if(userAcc.equals(account)&& userPwd.equals(password)) {
				session.setAttribute("loginLevel", loginRepository.doLogin(account).USERLEVEL);
				return "UserProfile";
			}
		    else {
	        	map.put("msg", "帳號或密碼錯誤");
	        	return "Login";
	        }
		}else {
			map.put("msg", "輸入的帳號不存在");
        	return "Login";
		}
	}
	@GetMapping("/Logout")
	public String UserLogout(HttpServletRequest request) {
		
        HttpSession session = request.getSession();
        
        if(session.getAttribute("loginLevel") != null ) {
        	session.removeAttribute("loginLevel");
        	return "Login";
        }
        return "Login";
		
	}
}
