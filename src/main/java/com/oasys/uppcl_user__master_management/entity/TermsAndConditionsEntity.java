package com.oasys.uppcl_user__master_management.entity;

import java.util.Date;
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
@Table(name = "terms_and_conditions")
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
public class TermsAndConditionsEntity extends Trackable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	
	@Column(name = "uuid", length = 16)
	private UUID id;

	@Column(name = "description")
	private String description;
	
	@Column(name = "remarks")
	private String remarks;
	

	@Column(name = "status")
	private Boolean status;
	
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	public TermsAndConditionsEntity() {
	}

	public TermsAndConditionsEntity(UUID id) {
		this.id = id;
	}

	public TermsAndConditionsEntity(String id) {
		this.id = UUID.fromString(id);
	}
}
