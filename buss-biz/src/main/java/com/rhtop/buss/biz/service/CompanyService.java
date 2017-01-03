/**
 * 代码声明
 */
package com.rhtop.buss.biz.service;

import java.util.List;
import com.rhtop.buss.common.entity.Company;

public interface CompanyService{
    
	/**
	 * 新增
	 */
	int insertCompany(Company company);
    
	/**
	 * 根据ID删除数据
	 */
	int deleteCompany(String companyId);
	
	/**
	 * 修改
	 */
	int updateCompany(Company company);
	
	/**
	 * 根据Id查找数据
	 */
	Company selectByPrimaryKey(String companyId);
	
	/**
	 * 根据条件查询列表
	 */
	List<Company> listCompanys(Company company);
	
    /**
	 * 根据条件分页查询列表
	 */
	List<Company> listPageCompany(Company company);
}