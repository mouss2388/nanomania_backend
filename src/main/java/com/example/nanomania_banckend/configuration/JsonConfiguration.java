package com.example.nanomania_banckend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.example.nanomania_banckend.models.Editor;

@Configuration
public class JsonConfiguration implements RepositoryRestConfigurer {

	@Bean
	public SpelAwareProxyProjectionFactory projectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}
	
	// on peut ici "forcer" spring data rest à renvoyer l'id des entité dans l'api automatique
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			config.exposeIdsFor(Editor.class);
		}
}
