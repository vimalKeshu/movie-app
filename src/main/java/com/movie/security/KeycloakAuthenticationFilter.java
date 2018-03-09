package com.movie.security;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;

public class KeycloakAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakAuthenticationFilter.class);
    public static final String DEFAULT_LOGIN_URL = "/sso/login";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    @Value("${keycloak-client.id}")
    private String clientId;
    
    @Value("${keycloak-client.secret}")
    private String clientSecret;    

    @Value("${keycloak.issuer}")
    private String issuer;

    @Value("${keycloak.jwk}")
    private String jwkUrl;    
        
    public static final RequestMatcher DEFAULT_REQUEST_MATCHER =
            new OrRequestMatcher(
                    /*new AntPathRequestMatcher(DEFAULT_LOGIN_URL),*/
                    new RequestHeaderRequestMatcher(AUTHORIZATION_HEADER)
            );
 
     
	protected KeycloakAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        //super.setAuthenticationManager(new NoOpAuthenticationManager());
	}
	
    public KeycloakAuthenticationFilter() {
    	this(DEFAULT_REQUEST_MATCHER);
    }	
	


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
/*		try {

			BearerTokenExtractor bearerTokenExtractor = new BearerTokenExtractor();
			Authentication auth = bearerTokenExtractor.extract(request);
			auth.setAuthenticated(true);
			String strAccessToken  = auth.getName();		
			Jwt tokenDecoded = JwtHelper.decode(strAccessToken);
			Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);
			verifyClaims(authInfo);
			//JWTClaimsSet jwtClaims = JWTClaimsSet.parse(strAccessToken);			
			 OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo, null);
			LOGGER.info("Token ................. "+strAccessToken);		
			LOGGER.info("Path info.................."+request.getRequestURI());
			LOGGER.info("Principal ................. "+user.getUsername());
			 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			 SecurityContextHolder.getContext().setAuthentication(authentication);	
			return authentication;			
		}catch(Exception ex) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			throw new BadCredentialsException("Could not obtain user details from token", ex);
			}*/
		return null;
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

}
