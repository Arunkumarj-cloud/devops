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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "plan_service_mapping")
public class PlanServiceMappingEntity extends Trackable{
	/**
	* 
	*/
	private static final long serialVersionUID = -2802869654224602894L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 16)
	private UUID id;

	@ManyToOne
	@JoinColumn(name="plan_id", referencedColumnName = "id")
	private PlanMasterEntity planMasterEntity;
	
	@ManyToOne
	@JoinColumn(name="service_id", referencedColumnName = "id")
	private ServiceMasterEntity serviceMasterEntity;
	
}
