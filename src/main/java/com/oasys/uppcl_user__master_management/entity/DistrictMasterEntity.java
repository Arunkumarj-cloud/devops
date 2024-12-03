package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "district_master")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class DistrictMasterEntity extends Trackable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	
	@Column(name = "district_name", nullable = false, length = 25)
	private String districtName;
	
	@Column(name = "district_code", nullable = false, length = 10)
	private String districtCode;
	
	@Column(name = "status", nullable = false)
	private Boolean status;
	
	@Column(name="state_code")
	private String stateCode;
	
	@Column(name="remarks")
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "stateid", referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "district_master_state_master_FK"))
	private StateMasterEntity stateId;
	
	public DistrictMasterEntity() {}

	public DistrictMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public DistrictMasterEntity(UUID id) {
	this.id = id;
	}

	

}
