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
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "corporation")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class CorporationMasterEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID2")
	@GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "corporation_name")
	private String corporationName;

	@Column(name = "corporation_code")
	private String corporationCode;

	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "corporation_district_FK"))
	private DistrictMasterEntity districtId;

	@Column(name = "status")
	private Boolean status;

	public CorporationMasterEntity() {
	}
	public CorporationMasterEntity(String uuid) {
		this.id = UUID.fromString(uuid);
	
	}
	public CorporationMasterEntity(UUID id) {
		this.id = id;
	}

	
}
