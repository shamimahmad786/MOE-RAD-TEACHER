package com.example.MOERADTEACHER.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.MOERADTEACHER.common.bean.DashboardBean;
import com.example.MOERADTEACHER.common.interfaces.DashboardInterface;
import com.example.MOERADTEACHER.common.modal.TeacherExperience;
import com.example.MOERADTEACHER.common.service.DashboardImpl;
import com.example.MOERADTEACHER.common.util.ApiPaths;
import com.example.MOERADTEACHER.common.util.CustomResponse;
import com.example.MOERADTEACHER.common.util.NativeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(ApiPaths.DashboardCtrl.CTRL)
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin(origins = {"https://kvsdemo.udiseplus.gov.in/","https://kvsonlinetransfer.kvs.gov.in","http://10.25.26.251:4200","http://10.25.26.10:4200","http://10.25.26.10:6200","http://demo.sdmis.gov.in","http://pgi.seshagun.gov.in","https://pgi.udiseplus.gov.in","http://pgi.udiseplus.gov.in","https://demopgi.udiseplus.gov.in","https://dashboard.seshagun.gov.in/","https://dashboard.udiseplus.gov.in"}, allowedHeaders = "*",allowCredentials = "true")
public class DashboardCtrl {

	 private static final Logger LOGGER = LoggerFactory.getLogger(DashboardCtrl.class);
//	@Autowired
//	DashboardImpl dashboardImpl;
	@Autowired
	DashboardInterface dashboardInterface;
	@Autowired
	NativeRepository nativeRepository;
	
	@RequestMapping(value = "/getDashboard", method = RequestMethod.POST,consumes =MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<CustomResponse> getDashboard(@RequestBody String data,@RequestHeader("username") String username) {
		ObjectMapper mapperObj = new ObjectMapper();
		DashboardBean expObj=null;
		try {
	 expObj = mapperObj.readValue(data, new TypeReference<DashboardBean>() {
			});
		}catch(Exception ex) {
			LOGGER.warn("--message--",ex);
		}
		return  ResponseEntity.ok(new CustomResponse(1,"sucess",dashboardInterface.getDashboard(expObj),"200"));
	}
	
	@RequestMapping(value = "/getDashboardDataOnMore", method = RequestMethod.POST,consumes =MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<CustomResponse> getDashboardDataOnMore(@RequestBody String data,@RequestHeader("username") String username) {
		return null;
		
	}
	
	@RequestMapping(value = "/getDashboardBasicCountDetails", method = RequestMethod.POST,consumes =MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<CustomResponse> getDashboardBasicCountDetails(@RequestBody String data,@RequestHeader("username") String username) {
		ObjectMapper mapperObj = new ObjectMapper();
		Map<Object,Object>	 expObj =null;
		try {
			 expObj = mapperObj.readValue(data, new TypeReference<Map<Object, Object>>() {
					});
				}catch(Exception ex) {
					LOGGER.warn("--message--",ex);
				}
		return  ResponseEntity.ok(new CustomResponse(1,"sucess",dashboardInterface.getDashboardBasicCountDetails(expObj),"200"));
	}
	
	
	@RequestMapping(value = "/getDashboardOnMoreClick", method = RequestMethod.POST,consumes =MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<CustomResponse> getDashboardOnMoreClick(@RequestBody String data,@RequestHeader("username") String username) {
		ObjectMapper mapperObj = new ObjectMapper();
		Map<Object,Object>	 expObj =null;
		try {
			 expObj = mapperObj.readValue(data, new TypeReference<Map<Object, Object>>() {
					});
				}catch(Exception ex) {
					LOGGER.warn("--message--",ex);
				}
		return  ResponseEntity.ok(new CustomResponse(1,"sucess",dashboardInterface.getDashboardOnMoreClick(expObj),"200"));
	}
	
	@RequestMapping(value = "/loginTest", method = RequestMethod.POST,consumes =MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<CustomResponse>  loginTest(@RequestBody String data,@RequestHeader("username") String username){
		
		System.out.println("in login test");
		ObjectMapper mapperObj = new ObjectMapper();
		String returnStatus;
		Map<Object,Object>	 expObj =null;
		try {
			 expObj = mapperObj.readValue(data, new TypeReference<Map<Object, Object>>() {
					});
				}catch(Exception ex) {
					LOGGER.warn("--message--",ex);
				}
		
		if(expObj.get("userId").equals("12345")  && expObj.get("userPassword").equals("same")) {
			returnStatus="1";
		}else {
			returnStatus="0";
		}
		
		return ResponseEntity.ok(new CustomResponse(1,"sucess",returnStatus,"200"));
		
	}
	
	
	
	
}
