package com.rhtop.buss.common.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;
import com.rhtop.buss.common.exception.BusException;

/**
 * 调用业务层REST服务
 * @author mgf
 *
 */
@Component("restService")
public class RestService {
	private static final Logger error = LoggerFactory.getLogger("error");
	@Autowired(required=false)
	@Qualifier("bizMessageTemplate")
	private MessagingTemplate bizmt;
	
	/**
	 * 调用业务服务
	 * @param service 业务服务ID
	 * @param method 业务服务请求方法POST|GET
	 * @param param 业务参数
	 * @return 返回JSON业务数据
	 */
	public String invokeMultiMap(String service,String method, Map<String,String[]> param){
		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Entry<String, String[]> entry: param.entrySet()){
			map.put(entry.getKey(), Lists.newArrayList(entry.getValue()));
		}
		MessageBuilder<?> builder = MessageBuilder.withPayload(map);
		builder.setHeader("service",service);
		builder.setHeader("http_requestUrl",service);
		builder.setHeader("http_requestMethod", method);
		Object payload = bizmt.sendAndReceive("servcieInvoke", builder.build()).getPayload();
		return (String)payload ;
	}
	/**
	 * 调用服务并返回文件流
	 * @param service 业务服务ID
	 * @param method 业务服务请求方法POST|GET
	 * @param param 业务参数
	 * @return 返回文件流
	 */
	public OutputStream invokeStream(String service,String method, Map<String,String[]> param){
		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Entry<String, String[]> entry: param.entrySet()){
			map.put(entry.getKey(), Lists.newArrayList(entry.getValue()));
		}
		MessageBuilder<?> builder = MessageBuilder.withPayload(map);
		builder.setHeader("service",service);
		builder.setHeader("http_requestUrl",service);
		builder.setHeader("http_requestMethod", method);
		OutputStream payload = (OutputStream) bizmt.sendAndReceive("servcieInvoke", builder.build()).getPayload();
		return payload;
	}
	public String invokeEntity(String service, Serializable entity){
		return null;
	}
	/**
	 * POST方式调用服务
	 * @param service 服务ID
	 * @param param 业务参数
	 * @return 业务数据
	 */
	public Map<String,Object> invoke(String service, Map<String,String[]> param){
		return invoke(service,"POST", param);
	}
	/**
	 * 调用业务服务
	 * @param service 业务服务ID
	 * @param method 业务服务请求方法POST|GET
	 * @param param 业务参数
	 * @return 返回Map业务数据
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> invoke(String service, String method,Map<String,String[]> param){
		return (Map<String,Object>)invoke(service, method, param,Map.class);
	}
	/**
	 * 调用业务服务，并指定返回类型
	 * @param service 业务服务ID
	 * @param method 业务服务请求方法POST|GET
	 * @param param 业务参数
	 * @param classes 返回类型
	 * @return 返回数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object invoke(String service, String method,Map<String,String[]> param,Class classes){
		String ret = invokeMultiMap(service,method,param);
		if(classes.equals(String.class)) return ret;
		ObjectMapper om = new ObjectMapper();
		try {
			return om.readValue(ret, classes);
		} catch (IOException e) {
			BusException be = new BusException("获取数据失败,service=" + service
					+ " param=" + param, "9999", e);
			error.error(e.getMessage(), be);
			throw  be;
		}
	}
	/**
	 * 调用业务服务，并指定返回的列表数据类型
	 * @param service 服务ID
	 * @param method 方法
	 * @param param 参数
	 * @param constructCollectionType 列表类型
	 * @return 返回列表数据
	 */
	public Object invoke(String service, String method, Map param,
			CollectionType constructCollectionType) {
		String ret = invokeMultiMap(service,method,param);
		ObjectMapper om = new ObjectMapper();
		try {
			return om.readValue(ret,constructCollectionType);
		} catch (IOException e) {
			BusException be = new BusException("获取数据失败,service=" + service
					+ " param=" + param, "9999", e);
			error.error(e.getMessage(), be);
			throw  be;
		}
	}

}
