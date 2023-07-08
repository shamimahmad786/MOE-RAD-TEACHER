package com.example.MOERADTEACHER.common.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.MOERADTEACHER.common.modal.TeacherExperience;
import com.example.MOERADTEACHER.common.modal.TeacherFormStatus;
import com.example.MOERADTEACHER.common.modal.TeacherProfile;
//import com.example.MOERADTEACHER.common.modal.TeacherPromotion;
import com.example.MOERADTEACHER.common.modal.TeacherTransferProfile;
import com.example.MOERADTEACHER.common.service.TeacherTransferProfileImpl;
import com.example.MOERADTEACHER.common.util.ApiPaths;
import com.example.MOERADTEACHER.common.util.CustomResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(ApiPaths.TeacherTransProCtrl.CTRL)
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeacherTransferProfileCtrl {

	@Autowired
	TeacherTransferProfileImpl  teacherTransferProfileImpl;
	
	
	@RequestMapping(value = "/saveTransProfile", method = RequestMethod.POST)
	@Transactional(rollbackFor = {Exception.class})
	public ResponseEntity<CustomResponse> saveTeacher(@RequestBody String data,@RequestHeader("username") String username) throws Exception {
		System.out.println(data);
		ObjectMapper mapperObj = new ObjectMapper();
		TeacherTransferProfile tdata=new TeacherTransferProfile();
		TeacherFormStatus formData=new TeacherFormStatus();
		try {
			tdata = mapperObj.readValue(data, new TypeReference<TeacherTransferProfile>() {
			});
		}catch(Exception ex) {
			ex.printStackTrace();
//			LOGGER.warn("--message--",ex);
		}
		
		System.out.println(tdata.getRelationWithEmplMdg());
		return ResponseEntity.ok(new CustomResponse(1,"sucess",teacherTransferProfileImpl.saveTeacher(tdata),"200"));
	}
	
	@RequestMapping(value = "/getTransProfile", method = RequestMethod.POST)
	@Transactional(rollbackFor = {Exception.class})
	public ResponseEntity<CustomResponse> getTransProfile(@RequestBody String data,@RequestHeader("username") String username) throws Exception {
	System.out.println("Calleded");
		ObjectMapper mapperObj = new ObjectMapper();
		TeacherTransferProfile tdata=new TeacherTransferProfile();
		TeacherFormStatus formData=new TeacherFormStatus();
		try {
			tdata = mapperObj.readValue(data, new TypeReference<TeacherTransferProfile>() {
			});
		}catch(Exception ex) {
			ex.printStackTrace();
//			LOGGER.warn("--message--",ex);
		}
		return ResponseEntity.ok(new CustomResponse(1,"sucess",teacherTransferProfileImpl.getTransProfile(tdata),"200"));
	}
	
	
	
	@RequestMapping(value = "/saveStationChoice", method = RequestMethod.POST)
//	@Transactional(rollbackFor = {Exception.class})
	public ResponseEntity<CustomResponse> saveStationChoice(@RequestBody String data,@RequestHeader("username") String username) throws Exception {
	System.out.println("Calleded");
		ObjectMapper mapperObj = new ObjectMapper();
		TeacherTransferProfile tdata=new TeacherTransferProfile();
		TeacherFormStatus formData=new TeacherFormStatus();
		try {
			tdata = mapperObj.readValue(data, new TypeReference<TeacherTransferProfile>() {
			});
		}catch(Exception ex) {
			ex.printStackTrace();
//			LOGGER.warn("--message--",ex);
		}
		return ResponseEntity.ok(new CustomResponse(1,"sucess",teacherTransferProfileImpl.saveStationChoice(tdata),"200"));
	}
	
	
	
}
