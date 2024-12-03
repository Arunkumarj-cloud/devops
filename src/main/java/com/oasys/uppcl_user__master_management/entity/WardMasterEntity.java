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
@Table(name = "ward")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WardMasterEntity extends Trackable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid",length = 16)
	private UUID id;

	@Column(name = "ward_name", nullable = false,length = 100)
	private String wardName;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "municipality_name_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="ward_municipality_FK"), nullable = false )
	private Muncipality municipalitynameId;

	public WardMasterEntity() {
	}

	public WardMasterEntity(String uuid) {
		this.id = UUID.fromString(uuid);

	}

	public WardMasterEntity(UUID id) {
	this.id = id;
	}
}
