package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "complaint")
@Data
//@Audited
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ComplaintReasonEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "reason_code", nullable = false, length = 10)
	private String reasonCode;
	
	@Column(name = "reason_name", nullable = false, length = 100)
	private String reasonName;
	
	@Column(name = "status",nullable = false)
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "reason_type_id", referencedColumnName = "id")
	private ReasonTypeEntity reasonTypeId;
	
	public ComplaintReasonEntity() {}

	public ComplaintReasonEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public ComplaintReasonEntity(UUID id) {
	this.id = id;
	}

}
