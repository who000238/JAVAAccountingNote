package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.CategoryModel;
@Transactional
public interface CategoryRepository extends JpaRepository<CategoryModel,String> {
	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE userid = ?1", nativeQuery = true)
    List<CategoryModel> getFindAll(String userid);
	
	@Query(value = "select * from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
    List<CategoryModel> getCategoryDetail(String ID);
	
	@Query(value = "select categoryname from  [AccountingNote].[dbo].[CategoryList] WHERE ID = ?1", nativeQuery = true)
    String getCategoryDetailName(String ID);
	
	@Modifying
	@Query(value = "INSERT INTO CategoryList \r\n"
			+ "	(categoryname\r\n"
			+ "	,createtime\r\n"
			+ "	,[CategoryList].Count\r\n"
			+ "	,userid)\r\n"
			+ "VALUES \r\n"
			+ "	(?1\r\n"
			+ "	,getdate()\r\n"
			+ "	,0\r\n"
			+ "	,?2); ", nativeQuery = true)
	Integer CreateNewCategory(String categoryname,String userid);
	@Modifying
	@Query(value = "DELETE FROM [dbo].[CategoryList]  WHERE ID=?1", nativeQuery = true)
	Integer deleteCat(String id);
}
