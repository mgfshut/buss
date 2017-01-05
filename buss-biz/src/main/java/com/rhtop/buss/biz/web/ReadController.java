package com.rhtop.buss.biz.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.utils.Jwt;

@Controller
@RequestMapping(value="service/readData")
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
	@ResponseBody
	public ReadResult<Member> viewMember(HttpServletRequest request,@RequestParam("memberId") String memberId){
		ReadResult<Member> readResult = new ReadResult<Member>();
		String token=request.getHeader("token");
		Map<String, Object> result=Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if("200".equals(result.get("code").toString())){
			Member member = memberService.selectByPrimaryKey(memberId);
			readResult.setResObject(member);
		}
		return readResult;
	}
}
