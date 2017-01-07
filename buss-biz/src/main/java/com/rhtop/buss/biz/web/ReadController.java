package com.rhtop.buss.biz.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rhtop.buss.biz.service.BusinessDiaryService;
import com.rhtop.buss.biz.service.CategoryService;
import com.rhtop.buss.biz.service.ContactsInfoService;
import com.rhtop.buss.biz.service.CustomerService;
import com.rhtop.buss.biz.service.MemberService;
import com.rhtop.buss.biz.service.RelCategoryPriceService;
import com.rhtop.buss.biz.service.RelCustomerCategoryService;
import com.rhtop.buss.common.entity.Customer;
import com.rhtop.buss.common.entity.Member;
import com.rhtop.buss.common.entity.ReadResult;
import com.rhtop.buss.common.entity.User;
import com.rhtop.buss.common.utils.Jwt;
import com.rhtop.buss.common.web.HtmlMessage;

/**
 * 对外接口的读取功能控制器，内部接口按照操作类型分为两类， 信息采集相关接口的命名为前缀In+四位编号0001依次递增,
 * 交易和合同相关接口的命名微前缀Dl+四位编号0001依次递增。
 * 
 * @author MakeItHappen
 * 
 */
@RestController
@RequestMapping(value = "service/readData")
// 设置跨域支持
@CrossOrigin
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

	@RequestMapping(method={RequestMethod.POST, RequestMethod.GET}, value="/viewMember")
	public ReadResult<Member> viewMember(HttpServletRequest request,
			@RequestBody Member member) {
		ReadResult<Member> readResult = new ReadResult<Member>();
		String token = request.getHeader("token");
		Map<String, Object> result = Jwt.validToken(token);
		readResult.setCode(result.get("code").toString());
		readResult.setMessage(result.get("message").toString());
		if ("200".equals(result.get("code").toString())) {
			Member mem = memberService.selectByPrimaryKey(member.getMemberId());
			readResult.setResObject(mem);
		}
		return readResult;
	}

	/**
	 * 客户信息查询 根据传入的用户id，查与之相关的客户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/R2001")
	public HtmlMessage listCustomer(String userId) {
		HtmlMessage htmlMsg = null;
		Customer customer = new Customer();
		customer.setCreateUser(userId);
		List<Customer> customerList = cusSer.selectCustomerInfo(customer);
		htmlMsg.setStatusCode("200");
//		htmlMsg.setEntity(JsonUtil.serialize(customerList)); //
		htmlMsg.setMessage("");
		return htmlMsg;
	}
}