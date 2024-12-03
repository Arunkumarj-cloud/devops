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

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "state_master")
@EqualsAndHashCode(callSuper = false)
public class StateMasterEntity  extends Trackable implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16 )
	private UUID id;
	
	@Column(name = "state_name",length= 25)
	private String stateName;
	
	@Column(name= "status")
	private Boolean status;
	
	@Column(name = "state_code")
	private String stateCode;
	
	@Column(name="state_type")
	private String stateType;
	
	@Column(name="tax_identification_no")
	private String taxIdentificationNo;
	
	
	public StateMasterEntity() {}

	public StateMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public StateMasterEntity(UUID id) {
	this.id = id;
	}
	
	

}
