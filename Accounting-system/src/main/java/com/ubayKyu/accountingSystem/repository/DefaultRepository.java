package com.ubayKyu.accountingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ubayKyu.accountingSystem.entity.DefaultPageModel;

public interface DefaultRepository extends JpaRepository<DefaultPageModel, String> {
	@Query(value = "select * from UserInfo", nativeQuery = true)
    List<DefaultPageModel> getFindAll();
	
	@Query(value = "SELECT TOP　(1) * FROM UserInfo ORDER BY CREATEDATE ASC", nativeQuery = true)
	List<DefaultPageModel> firstRecord();
	
	@Query(value = "SELECT TOP　(1) * FROM UserInfo ORDER BY CREATEDATE DESC", nativeQuery = true)
	List<DefaultPageModel> lastRecord();
	
	@Query(value = "SELECT COUNT(*)  FROM Accounting", nativeQuery = true)
	int recordCount();
	
	@Query(value = "SELECT COUNT(*) as Count FROM UserInfo", nativeQuery = true)
	int memberCount();
}
