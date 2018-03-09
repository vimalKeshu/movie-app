package com.movie.security;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.pojo.JwtMapper;
import com.movie.pojo.OpenIdConnectUserDetails;
import com.movie.pojo.Roles;

public class KeycloakAuthorizationFilter extends BasicAuthenticationFilter{
	private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakAuthorizationFilter.class);
    public static final String DEFAULT_LOGIN_URL = "/sso/login";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    @Value("${keycloak-client.id}")
    private String clientId;

    @Value("${keycloak.issuer}")
    private String issuer;

    @Value("${keycloak.jwk}")
    private String jwkUrl;  
    
	public KeycloakAuthorizationFilter() {
		super(new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				authentication.setAuthenticated(true);
		        return authentication;
			}
		});
	}


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

		BearerTokenExtractor bearerTokenExtractor = new BearerTokenExtractor();
		Authentication auth = bearerTokenExtractor.extract(req);
		String strAccessToken  = auth.getName();
		LOGGER.info("Token ................. "+strAccessToken); 
        if (strAccessToken == null || strAccessToken.isEmpty()) {
        	LOGGER.info("There is problem with token...............");
            chain.doFilter(req, res);
            return;
        }
        
		Jwt tokenDecoded = JwtHelper.decode(strAccessToken);
		Map<Object, Object> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);		   
		//Roles roles = (Roles) new ObjectMapper().readValue(authInfo.get("realm_access"), Roles.class);
		verifyClaims(authInfo);
	    List<Object> roles =  (List<Object>)(((LinkedHashMap<Object, Object>)authInfo.get("realm_access")).get("roles"));
	    LOGGER.info("Get roles ................. "+roles); 
		final OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo.get("sub").toString(),authInfo.get("preferred_username").toString(),createAuthority(roles));     
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);		       
        chain.doFilter(req, res);
    }	
    
    private RsaVerifier verifier(String kid){
		try {
	        JwkProvider provider = new UrlJwkProvider(new URL(jwkUrl));
	        Jwk jwk = provider.get(kid);
	        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());			
		}catch(Exception ex) {return null;}
    }	
	
	public void verifyClaims(Map claims) {
	    int exp = (int) claims.get("exp");
	    Date expireDate = new Date(exp * 1000L);
	    Date now = new Date();
	    if (expireDate.before(now) || !claims.get("iss").equals(issuer) || 
	      !claims.get("aud").equals(clientId)) {
	        throw new RuntimeException("Invalid claims");
	    }
	} 
	
	private Collection<GrantedAuthority> createAuthority(List<Object> roles){
		Collection<GrantedAuthority> authorities = null;
	       if(roles == null || roles.isEmpty()) {    	   
	    	   authorities = Arrays.asList(new GrantedAuthority() {    			
	    			@Override
	    			public String getAuthority() {
	    				return "ROLE_corvesta-user";
	    			}
	    		});   	   
	       }else {
	            authorities=roles.stream().map(role->new GrantedAuthority() {    		
	   			private static final long serialVersionUID = 1L;
	   			@Override
	       		public String getAuthority() {
	       			return "ROLE_"+role.toString();
	       		}
	       	}).collect(Collectors.toList()); 
	       }		
	     authorities.stream().forEach(role->LOGGER.info("Assigned Role....................................."+role.getAuthority()));  
		return authorities;
	}
}
