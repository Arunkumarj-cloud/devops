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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "service_type")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class ServiceTypeMasterEntity extends Trackable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "service_type", nullable = false, length = 100)
	private String serviceType;
	
	@Column(name = "status", nullable = false)
	private Boolean status;
	
//	@OneToMany(mappedBy = "serviceTypeId", cascade = CascadeType.ALL)
//	List<ServiceMasterEntity> serviceList;
	
	public ServiceTypeMasterEntity() {}

	public ServiceTypeMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public ServiceTypeMasterEntity(UUID id) {
	this.id = id;
	}
}
