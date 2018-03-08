package com.movie.security;

import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

public class OpenIDTokenProcessFilter extends AnonymousAuthenticationFilter{

	public OpenIDTokenProcessFilter(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}

}
