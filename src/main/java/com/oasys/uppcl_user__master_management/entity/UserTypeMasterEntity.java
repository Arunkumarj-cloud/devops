package com.oasys.uppcl_user__master_management.entity;

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
@Table(name = "user_type")
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserTypeMasterEntity extends Trackable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2547917254537531601L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid")
	private UUID id;
	
	@Column(name = "user_type")
	private String userType;

	@Column(name = "description")
	private String description;

	private Boolean status;

	public UserTypeMasterEntity() {
	}

	public UserTypeMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}

	public UserTypeMasterEntity(UUID id) {
		this.id = id;
	}

}
