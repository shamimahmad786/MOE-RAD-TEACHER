package com.example.MOERADTEACHER.common.uneecops.master.vo;

import lombok.Data;

@Data
public class RegionMasterResVo {
	private Integer id;
	private Integer regionCode;
	private String regionName;
	private Boolean isActive;
	public RegionMasterResVo(Integer id, Integer regionCode, String regionName, Boolean isActive) {
		super();
		this.id = id;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.isActive = isActive;
	}

	
}


