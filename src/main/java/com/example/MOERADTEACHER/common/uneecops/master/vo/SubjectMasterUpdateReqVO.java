package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class SubjectMasterUpdateReqVO {

	@NotNull(message="Please enter id")
	private Integer id;
	@NotNull(message="Please enter subject code")
	private String subjectCode;
	@NotNull(message="Please enter subject name")
	private String subjectName;
	private Boolean status;
}
