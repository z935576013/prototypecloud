package com.merak.lzptc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merak.lzptc.service.ServiceAlphaClient;

@RestController
public class SampleController {

	@Value("${foo}")
	String foo;

	@Autowired
	ServiceAlphaClient serviceAlphaClient;

	@RequestMapping(value = "/")
	String home(User user) {
		return foo + " Hello World! " + user.getMobile();
	}

	@RequestMapping(value = "/useAlpha")
	String useAlpha(User user) {
		String res = serviceAlphaClient.index(user.getMobile());
		return foo + " call alpha  ! " + res;
	}

}