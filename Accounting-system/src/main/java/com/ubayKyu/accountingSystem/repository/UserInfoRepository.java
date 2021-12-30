package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.UserInfoModel;

@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfoModel, String> {
	// 取得個人資訊
	@Query(value = "SELECT [UserInfo].[ID]\r\n" + "			     ,[UserInfo].[Account]\r\n"
			+ "			     ,[UserInfo].[name]\r\n" + "			     ,[UserInfo].[email]\r\n"
			+ "			     ,[UserInfo].[userlevel]\r\n"
			+ "			     ,FORMAT([UserInfo].createdate,'yyyy-MM-dd')AS createdate\r\n"
			+ "			     ,FORMAT([UserInfo].editdate,'yyyy-MM-dd')AS editdate\r\n"
			+ "			 FROM [AccountingNote].[dbo].[UserInfo]\r\n" + "			 WHERE ID= ?1", nativeQuery = true)
	List<UserInfoModel> getUserInfoByID(String userid);

	// 取得使用者清單
	@Query(value = "SELECT [UserInfo].[ID]\r\n" + "			     ,[UserInfo].[Account]\r\n"
			+ "			     ,[UserInfo].[name]\r\n" + "			     ,[UserInfo].[email]\r\n"
			+ "			     ,[UserInfo].[userlevel]\r\n"
			+ "			     ,FORMAT([UserInfo].createdate,'yyyy-MM-dd')AS createdate\r\n"
			+ "			     ,FORMAT([UserInfo].editdate,'yyyy-MM-dd')AS editdate\r\n"
			+ "			 FROM [AccountingNote].[dbo].[UserInfo]", nativeQuery = true)
	List<UserInfoModel> getAllUserInfo();

	// 更新個人資料
	@Modifying
	@Query(value = "UPDATE [dbo].[UserInfo]\r\n" + "   SET     \r\n" + "      [Name] = ?1\r\n"
			+ "      ,[Email] = ?2    \r\n" + " WHERE ID=?3", nativeQuery = true)
	Integer UpdateUserInfo(String name, String Mail, String userid);

	// 刪除會員
	@Modifying
	@Query(value = "DELETE FROM [dbo].[UserInfo] WHERE ID=?1", nativeQuery = true)
	Integer DeleteUser(String userid);

	// 新增會員
	@Modifying
	@Query(value = "INSERT INTO [dbo].[UserInfo]\r\n" + "           ([ID]\r\n" + "           ,[Account]\r\n"
			+ "           ,[PWD]\r\n" + "           ,[Name]\r\n" + "           ,[Email]\r\n"
			+ "           ,[UserLevel]\r\n" + "           ,[CreateDate]\r\n" + "           ,[EditDate])\r\n"
			+ "     VALUES\r\n" + "           (newid()\r\n" + "           ,?1\r\n" + "           ,'12345678'\r\n"
			+ "           ,?2\r\n" + "           ,?3\r\n" + "           ,?4\r\n" + "           ,getdate()\r\n"
			+ "           ,getdate())", nativeQuery = true)
	Integer CreateUser(String Account, String Name, String Email, String UserLevel);

	// 檢查帳號是否重複
	@Query(value = "SELECT * FROM [AccountingNote].[dbo].[UserInfo] WHERE Account=?1", nativeQuery = true)
	List<UserInfoModel> CheckAccExist(String Account);

	// 修改會員資料
	@Modifying
	@Query(value = "\r\n" + "UPDATE [dbo].[UserInfo]\r\n" + "   SET \r\n" + "     [Name] = ?1\r\n"
			+ "      ,[Email] = ?2\r\n" + "      ,[UserLevel] = ?3\r\n" + "      ,[EditDate] = GETDATE()\r\n"
			+ " WHERE ID=?4", nativeQuery = true)
	Integer ManagerUpdateUserInfo(String Name, String Email, String UserLevel, String ID);

}
