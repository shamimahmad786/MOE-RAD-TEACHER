package com.example.MOERADTEACHER;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
//import com.moe
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.MOERADTEACHER.common.exceptions.UserNotAuthorizedException;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
@Import(UserNotAuthorizedException.class)
public class MoeRadTeacherApplication {

	private static final Logger LOGGER=LoggerFactory.getLogger(MoeRadTeacherApplication.class);
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
//		TimerServiceApplication.main(null);
	}
	public static void main(String[] args) {
		SpringApplication.run(MoeRadTeacherApplication.class, args);
		LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
	
	}
}
