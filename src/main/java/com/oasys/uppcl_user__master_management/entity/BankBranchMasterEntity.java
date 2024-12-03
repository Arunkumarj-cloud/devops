package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "bank_branch")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class BankBranchMasterEntity extends Trackable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "branch_name")
	private String branchName;
	
	@Column(name = "branch_ifsc_code")
	private String branchIfscCode;
	
	@Column(name = "branch_contact_number")
	private Long branchContactNumber;
	
	@Column(name = "status")
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id")
	private DistrictMasterEntity districtId;
	
	@ManyToOne
	@JoinColumn(name = "bank_name_id", referencedColumnName = "id")
	private BankNameMasterEntity bankNameId;
	
	 @Column(name = "address",length= 100) 
	 private String address;
	
	 @Column(name = "std_code")
	 private Long stdCode;
	 
	 @Column(name ="landline_number")
	 private Long landlineNumber;
	 
	
	public BankBranchMasterEntity() {}

	public BankBranchMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public BankBranchMasterEntity(UUID id) {
	this.id = id;
	}

}
