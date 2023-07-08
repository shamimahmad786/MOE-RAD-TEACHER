package com.example.MOERADTEACHER.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
public class TransferDcBeans {

	public String kvCode;

	public Integer teacherId;

	public String teacherEmployeeCode;

	public Integer dcStayAtStation;

	public Integer dcPeriodAbsence;

	public Integer dcReturnStation;

	public Integer dcStayStationPoint;

	public String dcTenureHardPoint;
	
	public Integer dcPhysicalChallengedPoint;

	public Integer dcMdDfGroungPoint;

	public Integer dcLtrPoint;

	public Integer dcSpousePoint;

	public Integer dcSinglePoint;

	public Integer dcNonSopouseSinglePoint;

	public Integer dcRjcmNjcmPoint;
	
	public Integer dcTotalPoint;
	
	public Integer tcStayAtStation;
	public Integer tcPeriodAbsence;



	public Integer tcStayStationPoint;

	public Integer tcTenureHardPoint;

	public Integer tcPhysicalChallengedPoint;

	public Integer tcMdDfGroungPoint;

	public Integer tcLtrPoint;

	public Integer tcSpousePoint;

	public Integer tcSinglePoint;

	public Integer tcNonSopouseSinglePoint;

	public Integer tcRjcmNjcmPoint;
	
	public Integer tcTotalPoint;

	public String createdBy;

	public Date createdDateTime;

	public String updatedBy;

	public Date updateDateTime;
	
}
