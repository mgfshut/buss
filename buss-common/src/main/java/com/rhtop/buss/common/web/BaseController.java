package com.rhtop.buss.common.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.rhtop.buss.common.exception.BusException;
import com.rhtop.buss.common.message.i18n.LocalMessage;

public class BaseController {
	protected Logger  log = LoggerFactory.getLogger("error");
	protected HttpServletResponse servletResponse;
	@Autowired(required=false)
	private LocalMessage message;
	protected String getMessage(String code, Object...params){
		return message.getMessage(code, "未定义的错误");
	}
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}
	
	
	@ExceptionHandler(Exception.class)
    public @ResponseBody Object handleUncaughtException(Exception ex, WebRequest request, HttpServletResponse response) throws Exception {
		ex.printStackTrace();
		log.error("请求发生异常", ex);
//         if (AjaxUtils.isAjaxRequest(request)) {
//            response.setHeader("Content-Type", "application/json");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return "Unknown error occurred: " + ex.getMessage();
//        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
//            return null;
//        }
		if(ex instanceof BusException){
			BusException be = (BusException) ex;
			return new HtmlMessage(be.getCode(), be.getMessage());
		}
		else if(ex instanceof DataIntegrityViolationException){
			DataIntegrityViolationException dve = (DataIntegrityViolationException) ex;
			if(dve.getRootCause() instanceof SQLException){
				SQLException sqlex = (SQLException) dve.getRootCause();
				return new HtmlMessage(BusException.ERROR_CODE,getMessage("sql.errcode.E" + sqlex.getErrorCode()));
			}
		}
		else if(ex instanceof BindException){
			BindException be = (BindException) ex;
			List<ObjectError> list = be.getAllErrors();
			String message = "";
			for (ObjectError objectError : list) {
				message += objectError.getDefaultMessage() + "<br/>";
			}
			return new HtmlMessage(BusException.ERROR_CODE, message);
		}
		return new HtmlMessage(BusException.ERROR_CODE,"未知错误");
//		return new HtmlMessage()
    }
}
