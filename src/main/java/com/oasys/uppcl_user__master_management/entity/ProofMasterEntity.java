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
@Table(name = "proof")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class ProofMasterEntity extends Trackable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", length = 16)
	private UUID id;

	@Column(name = "proof")
	private String proofName;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "remarks")
	private String remarks;
	
	public ProofMasterEntity() {
	}

	public ProofMasterEntity(UUID id) {
		this.id = id;
	}

	public ProofMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}
}