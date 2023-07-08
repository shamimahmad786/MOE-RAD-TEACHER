package com.example.MOERADTEACHER.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.MOERADTEACHER.common.modal.TeacherTransferProfile;
import com.example.MOERADTEACHER.common.repository.TeacherTransferProfileRepository;
import com.example.MOERADTEACHER.common.util.CustomResponse;
import com.example.MOERADTEACHER.common.util.NativeRepository;

@Service
public class TeacherTransferProfileImpl {

	@Autowired
	TeacherTransferProfileRepository teacherTransferProfileRepository;
	
	
	@Autowired
	NativeRepository nativeRepository;

	
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
	
	public Map<String,Object> saveStationChoice(TeacherTransferProfile data) {
		Map<String,Object> mp=new HashMap<String,Object>();
		try {
			nativeRepository.updateQueries("update teacher_transfer_profile set apply_transfer_yn='"+data.getApplyTransferYn()+"', choice_kv1_station_code='"+data.getChoiceKv1StationCode()+"',choice_kv1_station_name='"+data.getChoiceKv1StationName()+"',choice_kv2_station_code='"+data.getChoiceKv2StationCode()+"',choice_kv2_station_name='"+data.getChoiceKv2StationName()+"',choice_kv3_station_code='"+data.getChoiceKv3StationCode()+"',choice_kv3_station_name='"+data.getChoiceKv3StationName()+"',choice_kv4_station_code='"+data.getChoiceKv4StationCode()+"',choice_kv4_station_name='"+data.getChoiceKv4StationName()+"',choice_kv5_station_code='"+data.getChoiceKv5StationCode()+"',choicekv5_station_name='"+data.getChoiceKv5StationName()+"' where teacher_id="+data.getTeacherId());		
			mp.put("status", 1);
		}catch(Exception ex) {
			mp.put("status", 0);
			ex.printStackTrace();
		}
		
		return mp;
		
	}
	
	
}
