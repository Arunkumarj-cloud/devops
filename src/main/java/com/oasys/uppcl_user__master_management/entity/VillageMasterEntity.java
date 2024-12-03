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
@Table(name = "village_master")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class VillageMasterEntity extends Trackable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	
	@Column(name = "village_name", nullable = false, length = 100)
	private String villageName;

	@Column(name = "status", nullable = false)
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "taluk_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="taluk_master_village_master_FK"), nullable = false)
	private TalukMasterEntity talukId;
	
	@Column(name = "code", nullable = false, length = 5)
	private String code;

	
	public VillageMasterEntity() {}

	public VillageMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public VillageMasterEntity(UUID id) {
	this.id = id;
	}

}


