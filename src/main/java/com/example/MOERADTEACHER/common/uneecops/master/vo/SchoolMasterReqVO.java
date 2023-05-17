package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class SchoolMasterReqVO {

	@NotNull(message = "please enter schoolCode")
	private Integer schoolCode;

	@NotNull(message = "please enter schoolName")
	private String schoolName;

	private Boolean status;
	@NotNull(message = "please enter shift")
	private String shift;
	
}
