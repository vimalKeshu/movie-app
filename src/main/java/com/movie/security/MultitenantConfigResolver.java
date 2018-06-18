package com.movie.security;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

public class MultitenantConfigResolver implements KeycloakConfigResolver {

	private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();
	private static AdapterConfig adapterConfig;

	@Override
	public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
		String realm = request.getHeader("realm");

		KeycloakDeployment deployment = cache.get(realm);
		if (null == deployment) {
			// not found on the simple cache, try to load it from the file system
			InputStream is = getClass().getResourceAsStream("/" + realm + "-keycloak.json");
			if (is == null) {
				throw new IllegalStateException("Not able to find the file /" + realm + "-keycloak.json");
			}
			deployment = KeycloakDeploymentBuilder.build(is);
			cache.put(realm, deployment);
		}

		return deployment;
	}

	public static void setAdapterConfig(AdapterConfig adapterConfig) {
		MultitenantConfigResolver.adapterConfig = adapterConfig;
	}

}