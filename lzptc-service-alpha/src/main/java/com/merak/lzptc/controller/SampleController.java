package com.merak.lzptc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@Value("${foo}")
    String foo;
	
	@RequestMapping(value = "/")
	String home(User user) {
		return foo+" Hello World! " + user.getMobile();
	}

}