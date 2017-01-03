/**
 * 代码声明
 */
package com.rhtop.buss.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.rhtop.buss.common.entity.Company;
import com.rhtop.buss.biz.mapper.CompanyMapper;
import com.rhtop.buss.biz.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyMapper companyMapper;
	
	@Override
	public int insertCompany(Company company) {
		return companyMapper.insertSelective(company);
	}

	@Override
	public int deleteCompany(String companyId) {
		return companyMapper.deleteByPrimaryKey(companyId);
	}

	@Override
	public int updateCompany(Company company) {
		return companyMapper.updateByPrimaryKeySelective(company);
	}
	
	@Override
	public Company selectByPrimaryKey(String companyId){
		return companyMapper.selectByPrimaryKey(companyId);
	}

	@Override
	public List<Company> listCompanys(Company company) {
		List<Company> companyList = companyMapper.listCompanys(company);
		return companyList;
	}
	
	@Override
	public List<Company> listPageCompany(Company company) {
		List<Company> companys = companyMapper.listPageCompany(company);
		return companys;
	}

}