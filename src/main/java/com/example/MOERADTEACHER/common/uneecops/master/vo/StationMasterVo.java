package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class StationMasterVo {
	private Integer Id;
	//@NotNull(message = "please enter stationCode")
	private Integer stationCode;
	@NotNull(message = "please enter stationName")
	private String stationName;

	private Boolean status;

}
