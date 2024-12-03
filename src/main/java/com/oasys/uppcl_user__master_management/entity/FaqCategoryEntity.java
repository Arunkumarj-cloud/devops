package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="faq_category")	    
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernatelazyinitializer", "handler" })
public class FaqCategoryEntity extends Trackable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length= 16)
	private UUID id;
	
	@Column(name = "category_name")
	String categoryName;
	
	@Column(name = "category_description")
	String categoryDescription;
	
	@Column(name="status")
	Boolean status;
	
	@Column(name="remarks")
	String remarks;
	
	public FaqCategoryEntity() {}

	public FaqCategoryEntity(String id) {
	this.id = UUID.fromString(id);
	}

	public FaqCategoryEntity(UUID id) {
	this.id = id;
	}
	

}
