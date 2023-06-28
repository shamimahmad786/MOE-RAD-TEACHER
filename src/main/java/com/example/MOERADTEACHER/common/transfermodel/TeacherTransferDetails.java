package com.example.MOERADTEACHER.common.transfermodel;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;


@Entity
@Table(name = "teacher_transfer_details", schema="transfer")
public class TeacherTransferDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "kv_code")
	public String kvCode;
	@Column(name = "teacher_id")
	public Integer teacherId;
	@Column(name = "transfer_id")
	public String transferId;
	@Column(name = "teacher_employee_code")
	public String teacherEmployeeCode;
	@Column(name = "dc_stay_at_station")
	public Integer dcStayAtStation;
	@Column(name = "dc_period_absence")
	public Integer dcPeriodAbsence;
	@Column(name = "dc_return_station")
	public Integer dcReturnStation;
	@Column(name = "dc_stay_station_point")
	public Integer dcStayStationPoint;
	@Column(name = "dc_tenure_hard_point")
	public Integer dcTenureHardPoint;
	@Column(name = "dc_physical_challenged_point")
	public Integer dcPhysicalChallengedPoint;
	@Column(name = "dc_md_df_groung_point")
	public Integer dcMdDfGroungPoint;
	@Column(name = "dc_ltr_point")
	public Integer dcLtrPoint;
	@Column(name = "dc_spouse_point")
	public Integer dcSpousePoint;
	@Column(name = "dc_single_point")
	public Integer dcSinglePoint;
	@Column(name = "dc_non_sopouse_single_point")
	public Integer dcNonSopouseSinglePoint;
	@Column(name = "dc_rjcm_njcm_point")
	public Integer dcRjcmNjcmPoint;
	@Column(name = "dc_total_point")
	public Integer dcTotalPoint;
	@Column(name = "created_by")
	public String createdBy;
	@Column(name = "created_date_time")
	public Date createdDateTime;
	@Column(name = "updated_by")
	public String updatedBy;
	@Column(name = "update_date_time")
	public Date updateDateTime;
	
	
	@PrePersist
	protected void onCreate() {
		createdDateTime =  new Timestamp(new Date().getTime());
		updateDateTime=  new Timestamp(new Date().getTime());
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getKvCode() {
		return kvCode;
	}


	public void setKvCode(String kvCode) {
		this.kvCode = kvCode;
	}


	public Integer getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}


	public String getTransferId() {
		return transferId;
	}


	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}


	public String getTeacherEmployeeCode() {
		return teacherEmployeeCode;
	}


	public void setTeacherEmployeeCode(String teacherEmployeeCode) {
		this.teacherEmployeeCode = teacherEmployeeCode;
	}


	public Integer getDcStayAtStation() {
		return dcStayAtStation;
	}


	public void setDcStayAtStation(Integer dcStayAtStation) {
		this.dcStayAtStation = dcStayAtStation;
	}


	public Integer getDcPeriodAbsence() {
		return dcPeriodAbsence;
	}


	public void setDcPeriodAbsence(Integer dcPeriodAbsence) {
		this.dcPeriodAbsence = dcPeriodAbsence;
	}


	public Integer getDcReturnStation() {
		return dcReturnStation;
	}


	public void setDcReturnStation(Integer dcReturnStation) {
		this.dcReturnStation = dcReturnStation;
	}


	public Integer getDcStayStationPoint() {
		return dcStayStationPoint;
	}


	public void setDcStayStationPoint(Integer dcStayStationPoint) {
		this.dcStayStationPoint = dcStayStationPoint;
	}


	public Integer getDcTenureHardPoint() {
		return dcTenureHardPoint;
	}


	public void setDcTenureHardPoint(Integer dcTenureHardPoint) {
		this.dcTenureHardPoint = dcTenureHardPoint;
	}


	public Integer getDcPhysicalChallengedPoint() {
		return dcPhysicalChallengedPoint;
	}


	public void setDcPhysicalChallengedPoint(Integer dcPhysicalChallengedPoint) {
		this.dcPhysicalChallengedPoint = dcPhysicalChallengedPoint;
	}


	public Integer getDcMdDfGroungPoint() {
		return dcMdDfGroungPoint;
	}


	public void setDcMdDfGroungPoint(Integer dcMdDfGroungPoint) {
		this.dcMdDfGroungPoint = dcMdDfGroungPoint;
	}


	public Integer getDcLtrPoint() {
		return dcLtrPoint;
	}


	public void setDcLtrPoint(Integer dcLtrPoint) {
		this.dcLtrPoint = dcLtrPoint;
	}


	public Integer getDcSpousePoint() {
		return dcSpousePoint;
	}


	public void setDcSpousePoint(Integer dcSpousePoint) {
		this.dcSpousePoint = dcSpousePoint;
	}


	public Integer getDcSinglePoint() {
		return dcSinglePoint;
	}


	public void setDcSinglePoint(Integer dcSinglePoint) {
		this.dcSinglePoint = dcSinglePoint;
	}


	public Integer getDcNonSopouseSinglePoint() {
		return dcNonSopouseSinglePoint;
	}


	public void setDcNonSopouseSinglePoint(Integer dcNonSopouseSinglePoint) {
		this.dcNonSopouseSinglePoint = dcNonSopouseSinglePoint;
	}


	public Integer getDcRjcmNjcmPoint() {
		return dcRjcmNjcmPoint;
	}


	public void setDcRjcmNjcmPoint(Integer dcRjcmNjcmPoint) {
		this.dcRjcmNjcmPoint = dcRjcmNjcmPoint;
	}


	public Integer getDcTotalPoint() {
		return dcTotalPoint;
	}


	public void setDcTotalPoint(Integer dcTotalPoint) {
		this.dcTotalPoint = dcTotalPoint;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedDateTime() {
		return createdDateTime;
	}


	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdateDateTime() {
		return updateDateTime;
	}


	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
	
	
	

}
