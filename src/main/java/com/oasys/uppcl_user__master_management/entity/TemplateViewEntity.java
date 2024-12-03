package com.oasys.uppcl_user__master_management.entity;

import java.util.Date;
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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "template_application_view")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TemplateViewEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "notification_channel")
	private String notificationChannel;

	@Column(name = "template_content")
	private String templateContent;

	@Column(name = "notification_network")
	private String notificationNetwork;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "app_id")
	private UUID applicationId;

	@Column(name = "application_name")
	private String applicationName;

	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "template_type")
	private String temp_type;
	
	@ManyToOne
	@JoinColumn(name = "service_category_id", referencedColumnName = "id",nullable = false,
	foreignKey = @ForeignKey(name = "template_service_category_FK"))
	private ServiceCategoryEntity serviceCategoryId;
	
	
	@ManyToOne
	@JoinColumn(name = "sub_category_id", referencedColumnName = "id",nullable = false,
	foreignKey = @ForeignKey(name = "template_sub_category_FK"))
	private SubCategoryEntity subCategoryId;
	public TemplateViewEntity() {

	}
	
	public TemplateViewEntity(UUID id) {
		this.id=id;
	}
	
	public TemplateViewEntity(String id) {
		this.id = UUID.fromString(id);
	}
}
