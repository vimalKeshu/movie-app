
package org.keycloak.adapters.springboot;

import org.apache.catalina.Context;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration.KeycloakTomcatContextCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.movie.security.MultitenantConfigResolver;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(KeycloakSpringBootProperties.class)
public class MultitenantConfiguration extends KeycloakAutoConfiguration {
	private KeycloakSpringBootProperties m_keycloakProperties;

	@Autowired
	@Override
	public void setKeycloakSpringBootProperties(final KeycloakSpringBootProperties keycloakProperties) {
		m_keycloakProperties = keycloakProperties;
		super.setKeycloakSpringBootProperties(keycloakProperties);
		MultitenantConfigResolver.setAdapterConfig(keycloakProperties);	}

	@Bean
	@ConditionalOnClass(name = { "org.apache.catalina.startup.Tomcat" })
	@Override
	public TomcatContextCustomizer tomcatKeycloakContextCustomizer() {
		return new MultitenantTomcatContextCustomizer(m_keycloakProperties);
	}

	static class MultitenantTomcatContextCustomizer extends KeycloakTomcatContextCustomizer {
		public MultitenantTomcatContextCustomizer(final KeycloakSpringBootProperties keycloakProperties) {
			super(keycloakProperties);
		}

		@Override
		public void customize(final Context context) {
			super.customize(context);
			final String name = "keycloak.config.resolver";
			context.removeParameter(name);
			context.addParameter(name, MultitenantConfigResolver.class.getName());
		}
	}
}