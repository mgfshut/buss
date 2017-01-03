package com.rhtop.buss.common.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginController extends BaseController {
	@RequestMapping("/login")
	public String index(HttpServletRequest request,HttpServletResponse response){
        String xmlHttpRequest = request.getHeader("X-Requested-With");
        if ( xmlHttpRequest != null ){
            if ( xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest") )  {
            	try {
            		response.setContentType("application/json; charset=UTF-8");
            		HtmlMessage htmlMessage = new HtmlMessage("600", "会话超时!");
            		ObjectMapper om = new ObjectMapper();
            		response.getWriter().print(om.writeValueAsString(htmlMessage));
				} catch (IOException e) {
					e.printStackTrace();
				}
                return null;
            }
        }
		return "login";
	}
}
