package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "commission_hierarchy")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
// @Audited
public class CommissionHierarchyEntity extends Trackable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@NotBlank
	@Column(name = "name", length = 25)
	private String name;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "model", length = 25)
	private String model;

	public CommissionHierarchyEntity() {
	}

	public CommissionHierarchyEntity(String id) {
		this.id = UUID.fromString(id);
	}

	public CommissionHierarchyEntity(UUID id) {
		this.id = id;
	}

}
