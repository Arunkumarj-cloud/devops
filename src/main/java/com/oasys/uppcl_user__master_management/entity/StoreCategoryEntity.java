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

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "store_category")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class StoreCategoryEntity extends Trackable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid",length =16)
	private UUID id;


	@Column(name = "store_category_name",length = 50)
	private String storeCategoryName;
	
	@ManyToOne
	@JoinColumn(name = "nature_of_business_id", referencedColumnName = "id")
	private NatureOfBusinessMasterEntity natureOfBusinessId;

	@Column(name="description",length=100)
	private String description;

	@Column(name="status")
	private Boolean status;


	public StoreCategoryEntity() {
	}

	public StoreCategoryEntity(UUID id) {
	this.id = id;
	}

	public StoreCategoryEntity(String id) {
	this.id = UUID.fromString(id);
	}

}
