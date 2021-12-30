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

	@Query(value = "select \r\n" + "[CategoryList].ID,\r\n" + "[CategoryList].categoryname,\r\n"
			+ "FORMAT(createtime,'yyyy-MM-dd')AS createtime,\r\n" + "Count(Accounting.category)as Count,\r\n"
			+ "[CategoryList].userid,\r\n" + "[CategoryList].categorybody\r\n"
			+ "from  [AccountingNote].[dbo].[CategoryList] \r\n"
			+ "LEFT JOIN Accounting ON [CategoryList].ID=Accounting.category\r\n"
			+ "WHERE  [CategoryList].userid = ?1\r\n"
			+ "GROUP BY CategoryList.ID,CategoryList.categoryname,CategoryList.createtime,CategoryList.categorybody,CategoryList.userid", nativeQuery = true)
	List<CategoryInterface> getFindAll(String userid);

	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
	List<CategoryModel> getCategoryDetail(String ID);

	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE userid = ?1", nativeQuery = true)
	List<CategoryModel> getCategoryDetailByUserID(String ID);

	@Query(value = "select categoryname from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
	String getCategoryDetailName(String ID);

	@Query(value = "select categorybody from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
	String getCategoryDetailBody(String ID);

	// 新增分類
	@Modifying
	@Query(value = "INSERT INTO [dbo].[CategoryList]\r\n" + "           ([categoryname]\r\n"
			+ "           ,[createtime]\r\n" + "           ,[userid]\r\n" + "           ,[categorybody])\r\n"
			+ "     VALUES\r\n" + "           (?1\r\n" + "           ,GETDATE()\r\n" + "           ,?2\r\n"
			+ "           ,?3) ", nativeQuery = true)
	Integer CreateNewCategory(String categoryname, String userid, String categorybody);

	// 刪除分類
	@Modifying
	@Query(value = "DELETE FROM [dbo].[CategoryList]  WHERE ID=?1", nativeQuery = true)
	Integer deleteCat(String id);

	// 刪除使用者所有分類
	@Modifying
	@Query(value = "DELETE FROM [dbo].[CategoryList]  WHERE userid=?1", nativeQuery = true)
	Integer deleteCatByuserid(String userid);

	// 確認該分類有無流水帳紀錄

	@Query(value = "SELECT COUNT([dbo].[CategoryList].ID) FROM [dbo].[CategoryList] LEFT JOIN Accounting ON [dbo].[CategoryList].ID = Accounting.category WHERE [dbo].Accounting.category = ?1", nativeQuery = true)
	Integer checkCateCount(String id);

	// 確認流水帳分類是否存在
	@Query(value = "SELECT * FROM [AccountingNote].[dbo].[CategoryList]\r\n"
			+ "WHERE categoryname= ?1 AND  categorybody= ?2 AND userid= ?3", nativeQuery = true)
	List<CategoryModel> checkCategoryExist(String categoryname, String categorybody, String userid);

	@Query(value = "SELECT * FROM [AccountingNote].[dbo].[CategoryList]\r\n" + "WHERE ID = ?1 ", nativeQuery = true)
	List<CategoryModel> checkCategoryExistById(String CategoryId);

	// 確認是否名稱重複
	@Query(value = "SELECT * FROM [AccountingNote].[dbo].[CategoryList]\r\n"
			+ "WHERE userid = ?1 AND categoryname = ?2", nativeQuery = true)
	List<CategoryModel> checkCategoryExistByIdAndName(String userid, String categoryname);

	// 編輯分類
	@Modifying
	@Query(value = "UPDATE [dbo].[CategoryList]\r\n" + "   SET \r\n" + "   [categoryname] = ?1   \r\n"
			+ "   ,[categorybody] = ?2\r\n" + " WHERE  ID =?3", nativeQuery = true)
	void updateCate(String categoryname, String categorybody, String CategoryID);

//	//取得分類名稱
//	@Query(value="SELECT categoryname FROM [AccountingNote].[dbo].[CategoryList] WHERE userid = ?1", nativeQuery = true)
//	List<CategoryModel> getCategoryList(String userid);
}
