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
@Table(name="api_master_entity")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ApiMasterEntity extends Trackable implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 16)
	private UUID id;
	@Column(length = 256)
	private String url;

	private String apiName;

	private String requestMethod;

	private String contentType;

	private String responseBodyType;

	private Boolean status;

	@Column(length = 256)
	private String description;
	
	private String feature;
	
	private String module;

	public ApiMasterEntity() {}

	public ApiMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public ApiMasterEntity(UUID id) {
	this.id = id;
	}

}
