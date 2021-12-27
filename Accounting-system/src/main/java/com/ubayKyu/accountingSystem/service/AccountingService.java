package com.ubayKyu.accountingSystem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.AccountingModel;
import com.ubayKyu.accountingSystem.repository.AccountingRepository;

@Service
public class AccountingService {
	@Autowired
	AccountingRepository accountingRepository;
	
	public List<AccountingModel> getAccountingAll(){
		return accountingRepository.findAll();
	}
	
	public Optional<AccountingModel> getAccountingByID(String AccountingID){
		return accountingRepository.findById(AccountingID);
	}
	// 總小計
	public int getTotalAmountByUserid(String userid) {
		Integer GetAmount = accountingRepository.getTotalGetAmountByUserid(userid);
		if(GetAmount == null) {
			GetAmount=0;
		}
		Integer OutAmount = accountingRepository.getTotalOutAmountByUserid(userid);
		if(OutAmount == null) {
			OutAmount=0;
		}
		Integer Total = GetAmount-OutAmount;
		
		return Total ;
	}
	 // 月小記
    public int getThisMonthTotalAmountByUserId(String userId){
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getMonth().length(now.isLeapYear()), 23, 59, 59);

        Integer GetAmount = accountingRepository.getMonthTotalGetAmountByUserId(userId, start, end);
        if(GetAmount == null) {
			GetAmount=0;
		}
        Integer OutAmount = accountingRepository.getMonthTotalOutAmountByUserId(userId, start, end);
        if(OutAmount == null) {
			OutAmount=0;
		}
        Integer Total = GetAmount-OutAmount;
        return Total ;
    }
}
