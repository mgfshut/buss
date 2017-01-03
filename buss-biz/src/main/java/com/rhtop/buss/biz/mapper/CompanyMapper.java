/**
 * 代码声明
 */
package com.rhtop.buss.biz.mapper;

import java.util.List;
import com.rhtop.buss.common.entity.Company;

public interface CompanyMapper {
	
	/**
	 * 根据条件新增对象
	 */
    int insertSelective(Company company);
	/**
	 * 根据主键删除对象
	 */
    int deleteByPrimaryKey(String companyId);
    /**
	 * 根据条件修改对象
	 */
    int updateByPrimaryKeySelective(Company company);
    /**
	 * 根据主键查询对象
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