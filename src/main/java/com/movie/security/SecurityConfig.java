package com.movie.security;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


@Configuration
@EnableWebSecurity	
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    
	 public SecurityConfig() {
	        super(true);
	    }	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
        http
        .csrf()
        .disable()      
        .addFilterAfter(registration().getFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()                       
        .antMatchers("/movie/**")
        .authenticated()
        ;
	}
	
    @Bean
    public KeycloakAuthenticationFilter getKeycloakAuthenticationFilter() {
        final KeycloakAuthenticationFilter filter = new KeycloakAuthenticationFilter(); 
        return filter;
    }
    
/*    @Bean
    public KeycloakAuthorizationFilter getKeycloakAuthorizationFilter() {
        final KeycloakAuthorizationFilter filter = new KeycloakAuthorizationFilter(new NoOpAuthenticationManager());
        return filter;
    }  */  
	
    @Bean
    public FilterRegistrationBean<Filter> registration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setFilter(getKeycloakAuthenticationFilter());
        registration.setEnabled(false);        
        return registration;
    }	
	
/*    *//**
     * This repository contains all known client registrations. This is only one-
     * Fan-out to different clients is done by Keycloak if necessary.
     *
     * @param clientProperties
     * @return
     *//*
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.asClientRegistration());
    }
    
	*//**
     * And this is the only interesting part here. The keycloak realm
     * is transformed to a so called ClientRegistration. ClientRegistrations
     * are used by Spring Security 5 to define different OAuth providers
     * @return
     *//*
    public ClientRegistration asClientRegistration() {
    	
    	String realm = "SpringBootKeycloak";
    	String serverUrl="http://localhost:8180/auth";
    	String id="movie-app";
    	String name="movie-app";
    	String secret="4e814fef-5b01-4491-a308-2ac7a55660ae";
    	String scope1="openid";
    	String scope2="profile";
        final String openIdConnectBaseUri
            = serverUrl + "/realms/" + realm + "/protocol/openid-connect";
        
        return ClientRegistration.withRegistrationId(realm)
            .clientId(id)
            .clientSecret(secret)
            .clientName(name)
            .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope(scope1,scope2)
            .authorizationUri(openIdConnectBaseUri + "/auth")
            .tokenUri(openIdConnectBaseUri + "/token")
            .jwkSetUri(openIdConnectBaseUri + "/certs")
            .userInfoUri(openIdConnectBaseUri + "/userinfo")
            .userNameAttributeName("preferred_username")
            .build();
    }    
    */
			
}
