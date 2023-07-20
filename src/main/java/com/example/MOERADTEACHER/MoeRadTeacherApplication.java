package com.example.MOERADTEACHER;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
//import com.moe
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.MOERADTEACHER.common.exceptions.UserNotAuthorizedException;
import com.example.MOERADTEACHER.common.util.NativeRepository;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableCaching
@EnableScheduling
@Import(UserNotAuthorizedException.class)
public class MoeRadTeacherApplication {

	private static final Logger LOGGER=LoggerFactory.getLogger(MoeRadTeacherApplication.class);
	
	@Autowired
	NativeRepository nativeRepository;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
//		TimerServiceApplication.main(null);
	}
	public static void main(String[] args) {
		SpringApplication.run(MoeRadTeacherApplication.class, args);
		LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
	}
	
	
//	 @Scheduled(fixedDelay = 7000)
//	  public void update() throws InterruptedException {
//		 nativeRepository.updateQueries("update public.teacher_work_experience twe set udise_school_name= ksm.kv_name  from kv.kv_school_master ksm where ksm.kv_code = twe.kv_code  and (udise_school_name is null or udise_school_name='') ");
//		 nativeRepository.updateQueries("update public.teacher_work_experience set kv_code = udise_sch_code where kv_code is null");
//		 nativeRepository.updateQueries("update public.teacher_profile set teacher_dob  = TO_CHAR(teacher_dob::timestamptz, 'YYYY-MM-DD')  where length(teacher_dob) = 24");
//		 nativeRepository.updateQueries("update public.teacher_profile set work_experience_work_start_date_present_kv  = TO_CHAR(work_experience_work_start_date_present_kv::timestamptz, 'YYYY-MM-DD') where length(work_experience_work_start_date_present_kv) = 24");
//		 nativeRepository.updateQueries("update public.teacher_profile set work_experience_position_type_present_station_start_date  = TO_CHAR(work_experience_position_type_present_station_start_date::timestamptz, 'YYYY-MM-DD') where length(work_experience_position_type_present_station_start_date) = 24 ");
//		 nativeRepository.updateQueries("update public.teacher_profile set last_promotion_position_date  = TO_CHAR(last_promotion_position_date::timestamptz, 'YYYY-MM-DD') where length(last_promotion_position_date) = 24");
//	  }
	
	
	
	
	
}
