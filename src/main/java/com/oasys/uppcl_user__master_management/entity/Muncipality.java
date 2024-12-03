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
@Table(name = "muncipality")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class Muncipality extends Trackable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@Column(name = "municipality_name")
	private String municipalityName;

	@Column(name = "municipality_code")
	private String municipalityCode;

	@Column(name = "status")
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "muncipality_district_FK"))
	private DistrictMasterEntity districtId;

	public Muncipality() {

	}

	public Muncipality(UUID id) {
		this.id = id;

	}

	public Muncipality(String id) {
		this.id = UUID.fromString(id);
	}

}
