package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "subscription_master")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SubscriptionMasterEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "name",length= 25)
	private String subscriptionName;
	
	@Column(name = "description",length=100)
	private String description;
	
	@Column(name = "is_custom")
	private Boolean isCustom;

	@Column(name = "is_default")
	private Boolean isDefault;

	@Column(name = "num_of_days")
	private int numOfDays;

	@Column(name = "is_active")
	private Boolean isActive;

	public SubscriptionMasterEntity() {

	}

	public SubscriptionMasterEntity(UUID id) {
		this.id = id;

	}

	public SubscriptionMasterEntity(String id) {
		this.id = UUID.fromString(id);

	}

}
