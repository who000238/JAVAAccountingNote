package com.ubayKyu.accountingSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.dto.AccountingInterface;
import com.ubayKyu.accountingSystem.entity.AccountingModel;
import com.ubayKyu.accountingSystem.entity.CategoryModel;

@Transactional
public interface AccountingRepository extends JpaRepository<AccountingModel, String> {
	@Query(value = "select Accounting.id\r\n" + ",Accounting.userid\r\n" + ",Accounting.caption\r\n"
			+ ",Accounting.amount\r\n" + ",Accounting.acttype\r\n"
			+ ", FORMAT(Accounting.createdate,'yyyy-MM-dd')AS createdate\r\n" + ",Accounting.body\r\n"
			+ ",CategoryList.categoryname\r\n" + "From Accounting\r\n"
			+ "LEFT JOIN CategoryList ON Accounting.category=CategoryList.ID\r\n" + "where Accounting.userid= ?1\r\n"
			+ "", nativeQuery = true)
	List<AccountingInterface> getFindAll(String userid);

	@Query(value = "select * from  [AccountingNote].[dbo].[Accounting] WHERE ID = ?1", nativeQuery = true)
	List<AccountingModel> getAccountingDetail(String ID);

	// 刪除流水帳
	@Modifying
	@Query(value = "DELETE FROM [dbo].[accounting]  WHERE ID=?1", nativeQuery = true)
	Integer deleteAcc(String id);

	// 刪除使用者所有流水帳
	@Modifying
	@Query(value = "DELETE FROM [dbo].[Accounting]  WHERE userid=?1", nativeQuery = true)
	Integer deleteAllAccByuserid(String userid);

	// 取得支出流水帳總和
	@Query(value = "SELECT SUM(amount) FROM accounting WHERE userid = ?1 AND acttype = 0 ", nativeQuery = true)
	Integer getTotalOutAmountByUserid(String userid);

	// 取得收入流水帳總和
	@Query(value = "SELECT SUM(amount) FROM accounting WHERE userid = ?1 AND acttype = 1 ", nativeQuery = true)
	Integer getTotalGetAmountByUserid(String userid);

	// 取得當月支出流水帳總和
	@Query(value = "SELECT SUM(amount) FROM accounting " + "WHERE userid = ?1 AND acttype = 0 "
			+ "AND createdate BETWEEN ?2 AND ?3", nativeQuery = true)
	Integer getMonthTotalOutAmountByUserId(String userId, LocalDateTime startDate, LocalDateTime endDate);

	// 取得當月收入流水帳總和
	@Query(value = "SELECT SUM(amount) FROM accounting " + "WHERE userid = ?1 AND acttype = 1 "
			+ "AND createdate BETWEEN ?2 AND ?3", nativeQuery = true)
	Integer getMonthTotalGetAmountByUserId(String userId, LocalDateTime startDate, LocalDateTime endDate);

	// 新增流水帳
	@Modifying
	@Query(value = "INSERT INTO [dbo].[Accounting]\r\n" + "           ([userid]\r\n" + "           ,[caption]\r\n"
			+ "           ,[amount]\r\n" + "           ,[acttype]\r\n" + "           ,[createdate]\r\n"
			+ "           ,[body]\r\n" + "           ,[category])\r\n" + "     VALUES\r\n" + "           (?1\r\n"
			+ "           ,?2\r\n" + "           ,?3\r\n" + "           ,?4\r\n" + "           ,getdate()\r\n"
			+ "           ,?5\r\n" + "           ,?6)", nativeQuery = true)
	Integer CreateNewAccounting(String userid, String caption, String amount, String acttype, String body,
			String category);

	// 編輯流水帳
	@Modifying
	@Query(value = "UPDATE [dbo].[Accounting]\r\n" + "   SET \r\n" + "      [caption] = ?1\r\n"
			+ "      ,[amount] = ?2\r\n" + "      ,[acttype] = ?3\r\n" + "      ,[createdate] = GETDATE()\r\n"
			+ "      ,[body] = ?4\r\n" + "      ,[category] = ?5\r\n" + " WHERE ID=?6", nativeQuery = true)
	Integer UpdateAccounting(String caption, String amount, String acttype, String body, String category,
			String accountingID);
}
