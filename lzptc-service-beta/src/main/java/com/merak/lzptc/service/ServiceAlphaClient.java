package com.merak.lzptc.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-alpha", fallback = ServiceAlphaClientHystric.class)
public interface ServiceAlphaClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	String index(@RequestParam(value = "mobile") String mobile);
}
