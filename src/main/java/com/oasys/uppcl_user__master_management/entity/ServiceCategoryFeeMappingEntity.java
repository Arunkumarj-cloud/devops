package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;


import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "service_category_fee_mapping")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ServiceCategoryFeeMappingEntity extends Trackable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",length = 16)
	private UUID id;

	@OneToOne
	@JoinColumn(name = "service_category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "merchant_details_merchant_fk"))
	private ServiceCategoryEntity serviceCategoryEntity;
	
	@Column(name = "amount")
	private Double amount;


}
