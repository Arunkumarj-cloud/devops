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

@Data
@Entity
@Table(name = "template")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class TemplateEntity extends Trackable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "notification_channel")
	private String type;

	
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "template_type")
	private String temp_type;
	@Column(name = "app_id")
	private UUID appId;

	@Column(name = "status")
	private Boolean status;
	@Column(name = "template_id")
	private String template_id;
	@Column(name = "notification_network")
	private String notificationNetwork;
	
	@ManyToOne
	@JoinColumn(name = "service_category_id", referencedColumnName = "id",nullable = false,
	foreignKey = @ForeignKey(name = "template_service_category_FK"))
	private ServiceCategoryEntity serviceCategoryId;
	
	
	@ManyToOne
	@JoinColumn(name = "sub_category_id", referencedColumnName = "id",nullable = false,
	foreignKey = @ForeignKey(name = "template_sub_category_FK"))
	private SubCategoryEntity subCategoryId;
	
	
	public TemplateEntity() {}
	
	public TemplateEntity(UUID id) {
		this.id= id;
	}
	public TemplateEntity(String id) {
		this.id = UUID.fromString(id);
	}
	
	@Column(name = "template_content")
	private String content;


}
