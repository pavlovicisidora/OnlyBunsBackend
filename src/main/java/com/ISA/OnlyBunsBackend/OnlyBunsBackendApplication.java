package com.ISA.OnlyBunsBackend;

import jdk.javadoc.doclet.Doclet;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.cache.Caching;
import java.net.URISyntaxException;


@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
public class OnlyBunsBackendApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CacheManager cacheManager() throws URISyntaxException {
		return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
				getClass().getResource("/ehcache.xml").toURI(), getClass().getClassLoader()));
	}


	public static void main(String[] args) {
		SpringApplication.run(OnlyBunsBackendApplication.class, args);
	}

}
