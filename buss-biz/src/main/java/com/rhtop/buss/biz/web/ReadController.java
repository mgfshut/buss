package com.rhtop.buss.biz.web;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ResultInfo;

/**
 * 
 */
@RestController
@RequestMapping(value = "service/readData")
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
	@Autowired
	private MemberService memberService;

	@RequestMapping("/viewMember")
	public ResultInfo viewMember(@RequestParam("body") String body) {
		JSONObject jsonObject=JSONObject.fromObject(body);
		Member mem = (Member)JSONObject.toBean(jsonObject, Member.class);
		ResultInfo readResult = new ResultInfo();
		Member member = memberService.selectByPrimaryKey(mem.getMemberId());
		readResult.setCode("200");
		readResult.setResObject(member);
		return readResult;
	}

}