
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

	@Entity
	@Table(name = "sub_category")
	@Data
	@EqualsAndHashCode(callSuper=false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	//@Audited
	public class SubCategoryEntity extends Trackable{/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(generator = "uuid2")
		@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
		@Column(name = "id", length = 16)
		private UUID id;
		
		@Column(name = "name", nullable = false, length = 25)
		private String name;
		
		@Column(name = "status", nullable = false)
		private Boolean status;
		
		@ManyToOne
		@JoinColumn(name = "servicecategoryid", referencedColumnName = "id",nullable = false,
		foreignKey = @ForeignKey(name = "sub_category_service_category_FK"))
		private ServiceCategoryEntity serviceCategoryId;
		
		@Column(name = "min_amount")
		private Double minAmount;
		
		@Column(name = "max_amount")
		private Double maxAmount;
		
		@Column(name = "subscriber_id")
		private String subscriberId;
		
		@Column(name = "length")
		private String length;
		
		@Column(name = "constant_name")
		private String constantName;
		
		public SubCategoryEntity() {}

		public SubCategoryEntity(String id) {
		this.id = UUID.fromString(id);
		}

		public SubCategoryEntity(UUID id) {
		this.id = id;
		}

		
		@Transient
		public UUID serviceProviderId;
		
		@Transient
		public String serviceProviderName;
		
		@Column(name = "remarks")
		private String remarks;
}



