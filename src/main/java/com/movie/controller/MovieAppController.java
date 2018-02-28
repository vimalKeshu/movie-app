package com.movie.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

/*   @GetMapping("/logout")
   public String logout(HttpServletRequest request) throws ServletException {
      request.logout();
      return "/";
   }*/	
	
/*   @GetMapping("/sso/login")
   public HttpServletResponse login(HttpServletRequest request,HttpServletResponse response) throws ServletException {
      //equest.logout();
      response.setStatus(302);
      response.addHeader("Location", "http://localhost:8180/auth");
      return response;
   }	*/	

}
