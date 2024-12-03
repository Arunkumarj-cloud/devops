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
@Table(name = "block")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class BlockMasterEntity extends Trackable {

	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "block_name", nullable = false, length = 25)
	private String blockName;
	
	@Column(name = "block_code", nullable = false, length = 10)
	private String blockCode;
	
	@Column(name = "status",nullable = false)
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="block_district_FK"), nullable = false)
	private DistrictMasterEntity districtId;
	
	public BlockMasterEntity() {}

	public BlockMasterEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public BlockMasterEntity(UUID id) {
	this.id = id;
	}
	
}
