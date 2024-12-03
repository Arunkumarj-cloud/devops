package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "field")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FieldMasterEntity extends Trackable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	
	@Column(name = "field_name",length=50)
	private String fieldName;

	@Column(name = "status")
	private Boolean status;
	
	
	public FieldMasterEntity() {}

	public FieldMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public FieldMasterEntity(UUID id) {
	this.id = id;
	}	
}