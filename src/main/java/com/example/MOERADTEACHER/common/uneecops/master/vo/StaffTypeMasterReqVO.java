package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StaffTypeMasterReqVO {

	@NotNull(message = "Please enter the staff type")
	private String staffType;
	
	private boolean status;


	
	
	

}
