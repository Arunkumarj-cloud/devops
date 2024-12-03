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

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "merchant_type")
@Getter@Setter
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
//@JsonIgnoreProperties(ignoreUnknown=true)

public class MerchantTypeMasterEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length=16)
	private UUID id;

	private Boolean status;
	
	@Column(name= "description",length = 100)
	private String description;

	@Column(name = "agent_type",length = 25)
	private String agentType;
	
	@Column(name = "remarks")
	private String remarks;

	public MerchantTypeMasterEntity() {
	}

	public MerchantTypeMasterEntity(UUID id) {
		this.id = id;
	}

	public MerchantTypeMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}

}
