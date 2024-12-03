package com.oasys.uppcl_user__master_management.entity;

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
@Table(name = "status")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class StatusMasterEntity extends Trackable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "status_name", nullable = false, length = 25)
	private String statusName;
	
	@Column(name = "status")
	private Boolean status;
	
	public StatusMasterEntity() {}

	public StatusMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public StatusMasterEntity(UUID id) {
	this.id = id;
	}

}
