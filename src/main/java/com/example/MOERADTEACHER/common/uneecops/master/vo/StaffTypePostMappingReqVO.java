package com.example.MOERADTEACHER.common.uneecops.master.vo;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class StaffTypePostMappingReqVO {

	@NotNull(message = "please enter staff type id")
	private Integer staffTypeId;
	@NotNull(message = "please enter  designation id")
	private Integer designationId;
	private boolean status;
}
