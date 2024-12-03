package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Entity
@Table(name = "airtel_branch_name")
@JsonIgnoreProperties({ "hibernatelazyinitializer", "handler" })
@Data
public class BranchEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	UUID id;
	
	@Column(name="bank_id")
	String bankid;
	
	@Column(name="state_code")
	String statecode;
	
	@Column(name="district_name")
	String districtname;
	
	@Column(name="ifsc_code")
	String ifsccode;
	
	@Column(name="branch_name")
	String branchname;
	
	@Column(name="bank_name")
	private String bankname;
	
	@Column(name="state_name")
	private String statename;
	
}


