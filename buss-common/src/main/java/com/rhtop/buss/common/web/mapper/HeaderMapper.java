package com.rhtop.buss.common.web.mapper;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;
import org.springframework.messaging.MessageHeaders;

public class HeaderMapper extends DefaultHttpHeaderMapper {
	private Map<String, String> map;
	
	@Override
	public void fromHeaders(MessageHeaders headers, HttpHeaders target) {
		super.fromHeaders(headers, target);
		for (Entry<String, String> entry: map.entrySet()) {
			target.set(entry.getKey(), entry.getValue());
		}
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
