package com.oasys.uppcl_user__master_management.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="customer_details")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CustomerDetails extends Trackable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", length = 16)
	private UUID uuid;
	
	@Column(name = "ifsc_code")
	private String ifscCode;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "state_name")
	private String stateName;
	
	@Column(name = "district_name")
	private String districtName;
			
	@Column(name = "branch_name")
	private String branchName;
	
	@Column(name = "phone_number")
	private Long phoneNumber;
	
	@Column(name = "ration_card_number")
	private String rationCardNumber;
	
	@Column(name = "smart_card_number")
	private String smartCardNumber;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "status")
	private Integer status;
	
	
}
