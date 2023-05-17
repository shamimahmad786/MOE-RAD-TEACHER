package com.example.MOERADTEACHER.common.bean;

import java.io.Serializable;

public class SchoolFetchTeacherBean implements Serializable{
	
private String currentUdiseSchCode;
private Integer teacherId;
private String businessUnitTypeCode;
public String getCurrentUdiseSchCode() {
	return currentUdiseSchCode;
}
public void setCurrentUdiseSchCode(String currentUdiseSchCode) {
	this.currentUdiseSchCode = currentUdiseSchCode;
}
public Integer getTeacherId() {
	return teacherId;
}
public void setTeacherId(Integer teacherId) {
	this.teacherId = teacherId;
}
public String getBusinessUnitTypeCode() {
	return businessUnitTypeCode;
}
public void setBusinessUnitTypeCode(String businessUnitTypeCode) {
	this.businessUnitTypeCode = businessUnitTypeCode;
}



}
