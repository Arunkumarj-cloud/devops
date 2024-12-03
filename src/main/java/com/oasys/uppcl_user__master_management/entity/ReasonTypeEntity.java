package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "reason_type_master")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
//@Audited
public class ReasonTypeEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@Column(name = "reason_type", nullable = false, length = 25)
	private String reasonType;
	
	@Column(name = "description", nullable = false, length = 100)
	private String description;

	@Column(name = "status", nullable = false)
	private Boolean status;


	@Column(name = "remarks")
	private String remarks;
	
	public ReasonTypeEntity() {}

	public ReasonTypeEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public ReasonTypeEntity(UUID id) {
	this.id = id;
	}
}
