package com.oasys.uppcl_user__master_management.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;
import com.oasys.constant.ActionType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "package_history")
public class PackageHistory extends Trackable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2802869654224602894L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 16)
	private UUID id;

	@Column(name = "package_name")
	private String packageName;

	@Column(name = "package_amount")
	private Double packageAmount;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "action_type")
	private ActionType actionType;

	@Column(name = "action_date")
	private LocalDateTime actionDateTime;

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "previous_amount")
	private Double previousAmount;
	
	@Column(name = "previous_status")
	private Boolean previousStatus;
	
	@Column(name = "updated_status")
	private Boolean updatedStatus;
	
	@Column(name = "package_id")
	private UUID packageId;
	
	@Column(name = "ip_address")
	private String ipAddress;

	public String getActionType() {
		return Objects.nonNull(this.actionType) ? this.actionType.getType() : null;
	}

	public String getActionDateTime() {
		return Objects.nonNull(actionDateTime) ? actionDateTime.format(DateTimeFormatter.ISO_DATE_TIME) : null;
	}

}
