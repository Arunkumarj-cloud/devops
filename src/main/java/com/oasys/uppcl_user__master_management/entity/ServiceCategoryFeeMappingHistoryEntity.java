package com.oasys.uppcl_user__master_management.entity;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;
import com.oasys.constant.ActionType;
import javax.persistence.ForeignKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "service_category_fee_mapping_history")
@EqualsAndHashCode(callSuper=false)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })


public class ServiceCategoryFeeMappingHistoryEntity extends Trackable{/**
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
	
	@Column(name = "previous_amount")
	private Double previousAmount;
	
	@Column(name = "updated_amount")
	private Double updatedAmount;
	
	@Column(name = "action_type")
	private ActionType actionType;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "remarks")
	private String remarks;
	
	public String getActionType() {
		return Objects.nonNull(this.actionType) ? this.actionType.getType() : null;
	}

}
