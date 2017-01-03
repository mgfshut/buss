package com.rhtop.buss.biz.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Company;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.biz.service.CompanyService;
import com.rhtop.buss.common.utils.DateUtils;

@Controller
@RequestMapping("service/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/insertCompany")
	public InfoResult<Company> insertCompany(@RequestBody Company company){
		InfoResult<Company> infoResult = new InfoResult<Company>();
		List<Company> companyList = companyService.listCompanys(new Company());
		company.setCompanyCode("GS000"+companyList.size());
		company.setCreateUser(company.getUpdateUser());
		company.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		company.setUpdateUser(company.getUpdateUser());
		company.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = companyService.insertCompany(company);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("新增成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("新增失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteCompany")
	public InfoResult<Company> deleteCompany(@RequestParam("companyId") String companyId){
		InfoResult<Company> infoResult = new InfoResult<Company>();
		int num = companyService.deleteCompany(companyId);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("删除成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("删除失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
     * 修改
     */
	@ResponseBody
	@RequestMapping("/updateCompany")
	public InfoResult<Company> updateCompany(@RequestBody Company company){
		InfoResult<Company> infoResult = new InfoResult<Company>();
		company.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = companyService.updateCompany(company);
		if (num > 0) {
			infoResult.setCode("200");
			infoResult.setMsg("修改成功");
		} else {
			infoResult.setCode("500");
			infoResult.setMsg("修改失败");
		}
	    return infoResult;// 200表示成功,500表示失败
	}
	/**
	 * 根据条件分页查询信息列表
	 */
	@ResponseBody
	@RequestMapping(value="/pager")
	public InfoResult<Company> listPageCompany(Page page,Company company){
		InfoResult<Company> infoResult = new InfoResult<Company>();
		company.setPage(page);
		List<Company> companyList = companyService.listPageCompany(company);
		infoResult.setCode("200");
		infoResult.setResList(companyList);
		infoResult.setPage(company.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Company> selectByPrimaryKey(@RequestParam("companyId") String companyId) {
		InfoResult<Company> infoResult = new InfoResult<Company>();
		infoResult.setCode("200");
		Company company = companyService.selectByPrimaryKey(companyId);
		infoResult.setResObject(company);
		return infoResult;
	}
}