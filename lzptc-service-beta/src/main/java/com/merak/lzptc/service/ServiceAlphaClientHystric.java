package com.merak.lzptc.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceAlphaClientHystric implements ServiceAlphaClient {
	@Override
	public String index(String mobile) {
		return "sorry hehe " + mobile;
	}
}