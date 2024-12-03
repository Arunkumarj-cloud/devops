//package com.oasys.uppcl_user__master_management.entity;
//
//import java.util.UUID;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ForeignKey;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.oasys.config.Trackable;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Entity
//@Table(name = "eb_service_provider")
//@Data
//@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//public class EBServiceProviderEntity extends Trackable {
//	/**
//	* 
//	*/
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(generator = "uuid2")
//	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//	@Column(name = "id", length = 16)
//	private UUID id;
//
//	@Column(name = "service_provider_name", nullable = false, length = 25)
//	private String serviceProviderName;
//
//	@Column(name = "status", nullable = false)
//	private Boolean status;
//
////	@ManyToOne
////	@JoinColumn(name = "servicecategoryid", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "service_provider_service_category_FK"))
////	private ServiceCategoryEntity serviceCategoryId;
//	
//	@ManyToOne
//	@JoinColumn(name = "stateid", referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "eb_service_provider_state_master_FK"))
//	private StateMasterEntity stateId;
//
//	public EBServiceProviderEntity() {}
//
//	public EBServiceProviderEntity(String id) {
//	this.id = UUID.fromString(id);
//	}
//
//	public EBServiceProviderEntity(UUID id) {
//	this.id = id;
//	}
//
//	@Column(name = "remarks")
//	private String remarks;
//
//}
