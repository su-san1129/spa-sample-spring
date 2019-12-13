package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/item_detail")
	public String item_detail() {
		return "item_details";
	}

}
