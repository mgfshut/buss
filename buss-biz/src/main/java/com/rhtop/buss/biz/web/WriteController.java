package com.rhtop.buss.biz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
/**
 * 对外接口的写入功能控制器，内部接口按照操作的表分为四类，操作客户相关表的命名为前缀Cu+四位编号0001依次递增，品类相关表为前缀Ca，交易相关表的为前缀Dl，合同相关表的为前缀Co.
 * @author MakeItHappen
 *
 */
@Controller
@RequestMapping(value="/writeData")
//配置跨域支持
@CrossOrigin(allowedHeaders = "*")
public class WriteController {
	@Autowired
	private CategoryService catSer;
	@Autowired
	private CustomerService cusSer;
	@Autowired
	private ContactsInfoService contactsSer;
	@Autowired
	private RelCategoryPriceService catPriSer;
	@Autowired
	private RelCustomerCategoryService cusCatSer;
	@Autowired
	private BusinessDiaryService busDiaSer;
}
