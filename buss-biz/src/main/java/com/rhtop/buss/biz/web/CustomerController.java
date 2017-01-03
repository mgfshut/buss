package com.rhtop.buss.biz.web;


import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Page;
import com.rhtop.buss.common.entity.InfoResult;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.common.utils.DateUtils;
import com.rhtop.buss.common.web.HtmlMessage;

@Controller
@RequestMapping("service/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
    /**
     * 新增
     */
	@ResponseBody
	@RequestMapping("/save")
	public HtmlMessage saveCustomer(@Valid @RequestParam(value = "userId") String userId,@Valid Customer customer){
		if(customer.getCustomerId() == null || "".equals(customer.getCustomerId())){
			String customerId = UUID.randomUUID().toString().replace("-", "");
			customer.setCustomerId(customerId);
			customer.setCreateUser(userId);
			customer.setCreateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			customer.setUpdateUser(userId);
			customer.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			customerService.insertCustomer(customer);
		}else{
			customer.setUpdateUser(userId);
			customer.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
			customerService.updateCustomer(customer);
		}
		return new HtmlMessage(customer);
	}
	/**
     * 删除
     */
	@ResponseBody
	@RequestMapping("/deleteCustomer")
	public InfoResult<Customer> deleteCustomer(@RequestParam("customerId") String customerId){
		InfoResult<Customer> infoResult = new InfoResult<Customer>();
		int num = customerService.deleteCustomer(customerId);
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
	@RequestMapping("/updateCustomer")
	public InfoResult<Customer> updateCustomer(Customer customer){
		InfoResult<Customer> infoResult = new InfoResult<Customer>();
		customer.setUpdateTime(DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
		int num = customerService.updateCustomer(customer);
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
	public InfoResult<Customer> listPageCustomer(Page page,Customer customer){
		InfoResult<Customer> infoResult = new InfoResult<Customer>();
		List<Customer> customerList = customerService.listPageCustomer(customer);
		infoResult.setCode("200");
		infoResult.setResList(customerList);
		infoResult.setPage(customer.getPage());
		return infoResult;
	}
	/**
	 * 根据主键查询
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByPrimaryKey")
	public InfoResult<Customer> selectByPrimaryKey(@RequestParam("customerId") String customerId) {
		InfoResult<Customer> infoResult = new InfoResult<Customer>();
		infoResult.setCode("200");
		Customer customer = customerService.selectByPrimaryKey(customerId);
		infoResult.setResObject(customer);
		return infoResult;
	}
}