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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "plan_master")
//@Audited
public class PlanMasterEntity extends Trackable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2802869654224602894L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 16)
	private UUID id;

	@Column(name = "plan_name",length=25)
	private String planName;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_default")
	private Boolean isDefault;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "priority")
	private Integer priority;
}
