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
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

	@Entity
	@Table(name = "service_slot")
	@Data
	@EqualsAndHashCode(callSuper=false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	//@Audited
	public class ServiceSlotEntity extends Trackable{/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(generator = "uuid2")
		@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
		@Column(name = "id", length = 16)
		private UUID id;
		
		@Column(name = "start_range")
		private long startRange;
		
		@Column(name = "end_range")
		private long endRange;
		
		@Transient
		private String slot;
		
		@Column(name = "status", nullable = false)
		private Boolean status;
		
		@Column(name = "position")
		private Long position;
		
		@Column(name = "remarks")
		private String remarks;
		
		@ManyToOne
		@JoinColumn(name = "servicecategoryid", referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "service_slot_service_category_FK"))
		private ServiceCategoryEntity serviceCategoryId;
		
		
		@ManyToOne
		@JoinColumn(name = "subcategoryid", referencedColumnName = "id",nullable = false,foreignKey = @ForeignKey(name = "service_slot_sub_category_FK"))
        private SubCategoryEntity subCategoryId;

		
		public ServiceSlotEntity() {}

		public ServiceSlotEntity(String id) {
		this.id = UUID.fromString(id);
		}

		public ServiceSlotEntity(UUID id) {
		this.id = id;
		}

		public String getSlot() {
			return startRange+"-"+endRange;
		}

		
		
		

}		


