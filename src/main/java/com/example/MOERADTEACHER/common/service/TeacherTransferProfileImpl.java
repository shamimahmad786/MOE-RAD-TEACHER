package com.example.MOERADTEACHER.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.MOERADTEACHER.common.modal.TeacherTransferProfile;
import com.example.MOERADTEACHER.common.repository.TeacherTransferProfileRepository;
import com.example.MOERADTEACHER.common.util.CustomResponse;

@Service
public class TeacherTransferProfileImpl {

	@Autowired
	TeacherTransferProfileRepository teacherTransferProfileRepository;
	
	public TeacherTransferProfile saveTeacher(TeacherTransferProfile data) throws Exception {
		System.out.println(data);
		System.out.println("data-->"+data.getChoiceKv1StationName());
		return teacherTransferProfileRepository.save(data);
	}
	
	public TeacherTransferProfile getTransProfile(TeacherTransferProfile data) throws Exception {
		System.out.println(data);
		System.out.println("data-->"+data.getTeacherId());
		return teacherTransferProfileRepository.findByTeacherId(data.getTeacherId());
	}
	
	
}
