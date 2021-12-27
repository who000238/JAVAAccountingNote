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
	@Query(value = "select Accounting.id\r\n" 
			+ ",Accounting.userid\r\n" 
			+ ",Accounting.caption\r\n"
			+ ",Accounting.amount\r\n" 
			+ ",Accounting.acttype\r\n"
			+ ", FORMAT(Accounting.createdate,'yyyy-MM-dd')AS createdate\r\n" 
			+ ",Accounting.body\r\n"
			+ ",CategoryList.categoryname\r\n" 
			+ "From Accounting\r\n"
			+ "LEFT JOIN CategoryList ON Accounting.category=CategoryList.ID\r\n" 
			+ "where Accounting.userid= ?1\r\n"
			+ "", nativeQuery = true)
	List<AccountingInterface> getFindAll(String userid);

	@Query(value = "select * from  [AccountingNote].[dbo].[Accounting] WHERE ID = ?1", nativeQuery = true)
	List<AccountingModel> getAccountingDetail(String ID);

	// 刪除流水帳
	// 刪除分類
	@Modifying
	@Query(value = "DELETE FROM [dbo].[accounting]  WHERE ID=?1", nativeQuery = true)
	Integer deleteAcc(String id);

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
	
}
