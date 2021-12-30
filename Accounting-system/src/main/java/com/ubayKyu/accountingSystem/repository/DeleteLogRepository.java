package com.ubayKyu.accountingSystem.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.DeleteLogModel;

@Transactional
public interface DeleteLogRepository extends JpaRepository<DeleteLogModel, String> {
	//刪除日誌寫入
	@Modifying
	@Query(value="INSERT INTO [dbo].[DeleteLog]\r\n"
			+ "           ([managerID]\r\n"
			+ "           ,[managerName]\r\n"
			+ "           ,[userID]\r\n"
			+ "           ,[userName]\r\n"
			+ "           ,[editDate])\r\n"
			+ "     VALUES\r\n"
			+ "           (?1,\r\n"
			+ "		   ?2,\r\n"
			+ "		   ?3,\r\n"
			+ "		   ?4,\r\n"
			+ "		   GETDATE())", nativeQuery = true)
	Integer CreateDeleteLog(String managerID,String managerName,String userID,String userName);
}
