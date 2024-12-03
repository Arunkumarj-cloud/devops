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
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "field_type")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FieldTypeMasterEntity extends Trackable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@NotBlank
	@Column(name = "field_type_name",length=50)
	private String fieldTypeName;

	@Column(name = "status")
	private Boolean status;

	public FieldTypeMasterEntity() {}

	public FieldTypeMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public FieldTypeMasterEntity(UUID id) {
	this.id = id;
	} 

}
