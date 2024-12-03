package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;


import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "gram_panchayat")
@Getter@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class GramPanchayat extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@Column(name = "panchayat_name", nullable = false, length = 100)
	private String panchayatName;
	
	@Column(name = "panchayat_code", nullable = false, length = 100)
	private String panchayatCode;

	@Column(name = "status", nullable = false)
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "block_id", referencedColumnName = "id", foreignKey = @ForeignKey(name="panchayat_block_FK"), nullable = false)
	private BlockMasterEntity blockId;

	public GramPanchayat() {

	}

	public GramPanchayat(UUID id) {
		this.id = id;
	}

	public GramPanchayat(String id) {
		this.id = UUID.fromString(id);
	}
 

}
