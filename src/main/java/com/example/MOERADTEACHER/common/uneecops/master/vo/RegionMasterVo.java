package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class RegionMasterVo {

	private Integer id;
	private Integer regionCode;
	@NotNull(message="please enter regionName")
	private String regionName;
	 
	private Boolean Status;


}
