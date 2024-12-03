package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "faq_category_list_view")
 @Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class FaqListViewEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@Column(name = "category_id")
	private UUID categoryId;
	
	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "status" )
	private Boolean status;

	public FaqListViewEntity() {

	}

	public FaqListViewEntity(UUID id) {
		this.id = id;
	}

	public FaqListViewEntity(String id) {
		this.id = UUID.fromString(id);
	}

}



