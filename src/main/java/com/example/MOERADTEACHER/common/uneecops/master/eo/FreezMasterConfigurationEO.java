package com.example.MOERADTEACHER.common.uneecops.master.eo;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="m_freez_master_configuration", schema="uneecops")
public class FreezMasterConfigurationEO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="operation")
	private String operation;
	@Column(name="year")
	private LocalDate year;
	@Column(name="from_date")
	private LocalDate fromDate;
	@Column(name="is_allowed")
	private boolean is_allowed;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_date")
	private Timestamp createdDate;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_date")
	private Timestamp updatedDate;
	@Column(name="is_active")
	private boolean status;

}
