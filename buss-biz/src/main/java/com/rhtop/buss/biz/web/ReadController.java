package com.rhtop.buss.biz.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;

@Controller
@RequestMapping(value="/readData")
public class ReadController {
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
