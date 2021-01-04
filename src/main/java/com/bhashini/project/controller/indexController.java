package com.bhashini.project.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class indexController {

	@GetMapping({"/","index"})
	public String index()
	{
		return "index";
	}
	
	
}
