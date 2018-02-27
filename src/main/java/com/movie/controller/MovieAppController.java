package com.movie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MovieAppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieAppController.class);
	@RequestMapping("/")
	public String home() {
		return "Welcome to movie app!";
	}

}
