package com.example.MOERADTEACHER.common.uneecops.master.eo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name="m_designation", schema="uneecops")
public class DesignationMasterEO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="post_code")
	private String postCode;
	@Column(name="post_name")
	private String postName;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	private Timestamp createdDate;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_date")
	private Timestamp updatedDate;
	@Column(name="status")
	private boolean status;
	
	
}
