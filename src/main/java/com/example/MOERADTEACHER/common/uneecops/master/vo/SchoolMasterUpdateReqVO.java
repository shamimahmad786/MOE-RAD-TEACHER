package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class SchoolMasterUpdateReqVO {
	private Integer id;
	@NotNull(message = "please enter schoolCode")
	private Integer schoolCode;

	@NotNull(message = "please enter schoolName")
	private String schoolName;
	private String shift;
private Integer schoolType;
	private Boolean status;
}
