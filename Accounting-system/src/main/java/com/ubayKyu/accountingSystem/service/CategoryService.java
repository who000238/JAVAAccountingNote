package com.ubayKyu.accountingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubayKyu.accountingSystem.entity.CategoryModel;
import com.ubayKyu.accountingSystem.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryModel> getCategoryAll(){
		return categoryRepository.findAll();
	}
	
	public boolean IsCategoryExist(String categoryname,String categorybody,String userid) {
		List<CategoryModel> list = categoryRepository.checkCategoryExist(categoryname, categorybody, userid);
		if(list.size()==0) {
			return false;
		}else {
			return true;
		}
	}
	public boolean IsCategoryExistbyCateID(String CategoryID) {
		List<CategoryModel> list = categoryRepository.checkCategoryExistById(CategoryID);
		if(list.size()==0) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean IsCategoryExistbyCateIDAndName(String userid,String CategoryName) {
		List<CategoryModel> list = categoryRepository.checkCategoryExistByIdAndName(userid,CategoryName);
		if(list.size()==0) {
			return false;
		}else {
			return true;
		}
	}

}
