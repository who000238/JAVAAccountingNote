package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.UserInfoModel;
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfoModel,String>{
	@Query(value="SELECT [UserInfo].[ID]\r\n"
			+ "      ,[UserInfo].[Account]\r\n"
			+ "      ,[UserInfo].[name]\r\n"
			+ "      ,[UserInfo].[email]\r\n"
			+ "      ,[UserInfo].[userlevel]\r\n"
			+ "      ,FORMAT([UserInfo].createdate,'yyyy-MM-dd')AS createdate\r\n"
			+ "  FROM [AccountingNote].[dbo].[UserInfo]\r\n"
			+ "  WHERE ID= ?1",nativeQuery = true)
	List<UserInfoModel> getFindAll(String userid);
	//更新個人資料
	@Modifying
	@Query(value="UPDATE [dbo].[UserInfo]\r\n"
			+ "   SET     \r\n"
			+ "      [Name] = ?1\r\n"
			+ "      ,[Email] = ?2    \r\n"
			+ " WHERE ID=?3",nativeQuery = true)
	Integer UpdateUserInfo(String name,String Mail,String userid);
}
