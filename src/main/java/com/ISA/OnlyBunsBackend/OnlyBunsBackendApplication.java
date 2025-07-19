package com.ISA.OnlyBunsBackend;

import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.spi.service.ServiceCreationConfiguration;
import org.ehcache.xml.XmlConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.ehcache.jsr107.EhcacheCachingProvider;


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

	/*@Value("${server.port}")
	private String serverPort;

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public CacheManager cacheManager() throws URISyntaxException, IOException {
		String baseCachePath = System.getProperty("user.home") + File.separator + "OnlyBunsBackend_caches";
		String instanceCachePath = baseCachePath + File.separator + "cache_" + serverPort;

		File dir = new File(instanceCachePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		System.out.println("Using Ehcache persistence directory: " + instanceCachePath);

		System.setProperty("ehcache.persistence.dir", dir.getAbsolutePath());


		URL ehcacheXmlUrl = applicationContext.getResource("classpath:ehcache.xml").getURL();

		CachingProvider cachingProvider = Caching.getCachingProvider();

		Properties properties = new Properties();

		javax.cache.CacheManager jcacheManager = cachingProvider.getCacheManager(
				ehcacheXmlUrl.toURI(),
				getClass().getClassLoader(),
				properties
		);

		return new JCacheCacheManager(jcacheManager);
	}*/


	public static void main(String[] args) {
		SpringApplication.run(OnlyBunsBackendApplication.class, args);
	}

}
