package com.movie.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class KeycloakOpenIdConnectConfig {
	
	@Value("${keycloak-client.id}")
	private String clientId;
	
	@Value("${keycloak-client.secret}")
	private String clientSecret;
	
	@Value("${keycloak-client.name}")
	private String clientName;	
	
	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${keycloak.auth-server-url}")
	private String serverUrl;
	
	@Bean
	public OAuth2ProtectedResourceDetails keyCloakOpenId() {
		final String openIdConnectBaseUri
        = serverUrl + "/realms/" + realm + "/protocol/openid-connect";
        final AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(openIdConnectBaseUri + "/token");
        details.setUserAuthorizationUri(openIdConnectBaseUri + "/auth");
        details.setScope(Arrays.asList("openid", "username"));
        //details.setPreEstablishedRedirectUri(redirectUri);
        details.setUseCurrentUri(false);
        return details;
    }
	
    @Bean
    public OAuth2RestTemplate keyCloakOpenIdTemplate(final OAuth2ClientContext clientContext) {
        final OAuth2RestTemplate template = new OAuth2RestTemplate(keyCloakOpenId(), clientContext);
        return template;    	
    }
}
