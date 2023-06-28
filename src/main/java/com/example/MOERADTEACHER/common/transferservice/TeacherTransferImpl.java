package com.example.MOERADTEACHER.common.transferservice;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.MOERADTEACHER.common.transfermodel.TeacherTransferDetails;
import com.example.MOERADTEACHER.common.transferrepository.TeacherTransferRepository;

@Service
@Transactional
public class TeacherTransferImpl {

	@Autowired
	TeacherTransferRepository teacherTransferRepository;
	
	public TeacherTransferDetails saveTransferDCTCPoints(TeacherTransferDetails data) throws Exception {
		return teacherTransferRepository.saveAndFlush(data);
	}
	
}
