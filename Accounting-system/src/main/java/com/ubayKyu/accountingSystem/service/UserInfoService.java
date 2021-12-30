//package com.ubayKyu.accountingSystem.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ubayKyu.accountingSystem.entity.UserInfoModel;
//import com.ubayKyu.accountingSystem.repository.UserInfoRepository;
//
//@Service
//public class UserInfoService {
//	@Autowired
//	private UserInfoRepository repository;
//	
//	public Optional<UserInfoModel> getUserInfoByID(String userid){
//		return repository.findById(userid);
//	}
//}
