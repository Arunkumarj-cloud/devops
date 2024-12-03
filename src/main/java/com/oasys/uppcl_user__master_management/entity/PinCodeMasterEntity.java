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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pincode_master", uniqueConstraints = @UniqueConstraint(columnNames = {
		"pin_code" }, name = "pincode_master_uniq_pin_code"))
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class PinCodeMasterEntity extends Trackable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "pin_code", nullable = false, length = 25)
	private String pinCode;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@Column(name = "division_name")
	private String divisionName;

	@Column(name = "remarks")
	private String remarks;
	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "pincode_master_district_master_FK"))
	private DistrictMasterEntity districtId;

	public PinCodeMasterEntity() {
	}

	public PinCodeMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}

	public PinCodeMasterEntity(UUID id) {
		this.id = id;
	}

}
