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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "error_code")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ErrorCodeEntity extends Trackable implements Serializable {

	private static final long serialVersionUID = -861954757569711375L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@Column(name = "response_code")
	private String responseCode;

	@Column(name = "description")
	private String description;

	@Column(name = "bhim_aadhaar_pay")
	private String bhimAadhaarPay;

	@Column(name = "receipt_required")
	private String receiptRequired;
	
	@Column(name = "status")
	private Boolean status;

}
