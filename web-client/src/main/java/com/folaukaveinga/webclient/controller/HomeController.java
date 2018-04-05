package com.folaukaveinga.webclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping("")
	public String home() {
		log.info("home");
		return "home";
	}
	
	@GetMapping("/signup")
	public String signup() {
		log.info("signup");
		return "signup";
	}
	
	@GetMapping("/login")
	public String login() {
		log.info("login");
		return "login";
	}
	
	@GetMapping("/order")
	public String order() {
		log.info("order");
		return "order";
	}
}
