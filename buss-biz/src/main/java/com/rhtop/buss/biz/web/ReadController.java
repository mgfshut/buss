package com.rhtop.buss.biz.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

/**
 * 对外接口的读取功能控制器，内部接口按照操作类型分为两类，
 * 信息采集相关接口的命名为前缀In+四位编号0001依次递增,
 * 交易和合同相关接口的命名微前缀Dl+四位编号0001依次递增。
 * @author MakeItHappen
 *
 */
@RestController
@RequestMapping(value="service/readData")
//设置跨域支持
@CrossOrigin(allowedHeaders="*")
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
