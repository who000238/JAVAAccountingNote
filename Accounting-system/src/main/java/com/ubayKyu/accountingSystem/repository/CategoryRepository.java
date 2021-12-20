package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.dto.CategoryInterface;
import com.ubayKyu.accountingSystem.entity.CategoryModel;

@Transactional
public interface CategoryRepository extends JpaRepository<CategoryModel, String> {
//	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE userid = ?1", nativeQuery = true)
//    List<CategoryModel> getFindAll(String userid);

	@Query(value = "select \r\n"
			+ "[CategoryList].ID,\r\n"
			+ "[CategoryList].categoryname,\r\n"
			+ "FORMAT(createtime,'yyyy-MM-dd')AS createtime,\r\n"
			+ "Count(Accounting.category)as Count,\r\n"
			+ "[CategoryList].userid,\r\n"
			+ "[CategoryList].categorybody\r\n"
			+ "from  [AccountingNote].[dbo].[CategoryList] \r\n"
			+ "LEFT JOIN Accounting ON [CategoryList].ID=Accounting.category\r\n"
			+ "WHERE  [CategoryList].userid = ?1\r\n"
			+ "GROUP BY CategoryList.ID,CategoryList.categoryname,CategoryList.createtime,CategoryList.categorybody,CategoryList.userid", nativeQuery = true)
	List<CategoryInterface> getFindAll(String userid);

	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
	List<CategoryModel> getCategoryDetail(String ID);

	@Query(value = "select categoryname from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
	String getCategoryDetailName(String ID);

	// 新增分類
	@Modifying
	@Query(value = "INSERT INTO CategoryList \r\n" + "	(categoryname\r\n" + "	,createtime\r\n"
			+ "	,[CategoryList].Count\r\n" + "	,userid)\r\n" + "VALUES \r\n" + "	(?1\r\n" + "	,getdate()\r\n"
			+ "	,0\r\n" + "	,?2); ", nativeQuery = true)
	Integer CreateNewCategory(String categoryname, String userid);

	// 刪除分類
	@Modifying
	@Query(value = "DELETE FROM [dbo].[CategoryList]  WHERE ID=?1", nativeQuery = true)
	Integer deleteCat(String id);

	// 確認該分類有無流水帳紀錄

	@Query(value = "SELECT COUNT([dbo].[CategoryList].ID) FROM [dbo].[CategoryList] LEFT JOIN Accounting ON [dbo].[CategoryList].ID = Accounting.category WHERE [dbo].Accounting.category = ?1", nativeQuery = true)
	Integer checkCateCount(String id);

}
