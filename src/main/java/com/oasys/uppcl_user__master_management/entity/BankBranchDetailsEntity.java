package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
@Table(name = "bank_branch_details")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BankBranchDetailsEntity  extends Trackable{


private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "bank_id")
	private String bankId;
	
	@Column(name = "ifsc_code")
	private String ifscCode;
	
	@Column(name = "state_code")
	private String stateCode;
	
	@Column(name = "district_name")
	private String districtName;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "state_name")
	private String stateName;
	
	@Column(name = "status")
	private Boolean status;
	
	 @Column(name = "std_code")
	 private Long stdCode;
	 
	 @Column(name ="landline_number")
	 private Long landlineNumber;
	 
	@Column(name = "branch_name")
	private String branchName;
	 
	@ManyToOne
	@JoinColumn(name = "bankname_id", referencedColumnName = "id",nullable = false, 
	foreignKey = @ForeignKey(name = "bankbranch_details_master_bankname_master_FK"))
	private BankNameMasterEntity bankNameId;
	
	
	@Column(name="branch_contact_number")
	private String branchContactNumber;
	
	@Column(name="address")
	private String address;
	
	
	@Column(name="remarks")
	private String remarks;
	
		/*@Column(name = "bank_name_id")
		private String bankNameId;*/
	
	public BankBranchDetailsEntity() {}

	public BankBranchDetailsEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public BankBranchDetailsEntity(UUID id) {
	this.id = id;
	}
}
