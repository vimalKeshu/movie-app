package com.movie.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MultitenantConfigResolver implements KeycloakConfigResolver {

	private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();
	private static String realmsJsonStr;

	@Override
	public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
		String realm = request.getHeader("realm");
		if (realm == null) {
			return cache.getOrDefault("None", new KeycloakDeployment());
		}
		if (realmsJsonStr == null) {
			throw new IllegalStateException("Not able to find the realm json!");
		}
		KeycloakDeployment deployment = cache.get(realm);
		if (null == deployment) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<Object, Object> realmsJson = objectMapper.readValue(realmsJsonStr, Map.class);
				if (realmsJson.get(realm) == null)
					throw new IllegalStateException("Not able to find the keycloak details of the realm, " + realm);
				Map<Object, Object> keycloakRealmDetails = (Map<Object, Object>) realmsJson.get(realm);

				AdapterConfig adapterConfig = new AdapterConfig();
				adapterConfig.setRealm(keycloakRealmDetails.get("realm").toString());
				adapterConfig.setAuthServerUrl(keycloakRealmDetails.get("auth-server-url").toString());
				adapterConfig.setResource(keycloakRealmDetails.get("resource").toString());
				adapterConfig.setSslRequired(keycloakRealmDetails.get("ssl-required").toString());

				deployment = KeycloakDeploymentBuilder.build(adapterConfig);
				cache.put(realm, deployment);
			} catch (Exception e) {
				throw new IllegalStateException("keycloak realm json for realm," + realm + " not valid.!");
			}
		}
		return deployment;
	}

	public static void setRealmsJsonStr(String realmsJsonStr) {
		MultitenantConfigResolver.realmsJsonStr = realmsJsonStr;
	}

}