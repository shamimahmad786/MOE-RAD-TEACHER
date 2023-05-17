package com.example.MOERADTEACHER.common.masterbean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_teacher_position_type", schema = "master")
public class PositionType {

	@Id
	@Column(name = "teacher_type_id")
	public Integer teacherTypeId;

	@Column(name = "udise_teacher_type_id")
	public Integer udiseTeacherTypeId;

	@Column(name = "teacher_type_name")
	public String teacherTypeName;
	@Column(name = "organization_teacher_type_id")
	public Integer organizationTeacherTypeId;
	@Column(name = "organization_teacher_type_name")
	public String organizationTeacherTypeName;
	@Column(name = "configured_business_unit_type_id")
	public Integer configuredBusinessUnitTypeId;
	@Column(name = "configured_business_unit_identity_code")
	public String configuredBusinessUnitIdentityCode;
	@Column(name = "active_status")
	public String activeStatus;
	@Column(name = "start_date")
	public String startDate;
	@Column(name = "end_date")
	public String endDate;
	@Column(name = "org_teacher_type_code")
	public String orgTeacherTypeCode;
	@Column(name = "application_id")
	public String applicationId;

}
