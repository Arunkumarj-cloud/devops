package com.oasys.uppcl_user__master_management.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "search_fields")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SearchFieldsEntity extends Trackable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;

	@Column(name = "name", nullable = false, length = 25)
	private String name;

	@Column(name = "service_id", nullable = false)
	private UUID serviceId;

	@Column(name = "show_order")
	private Integer showOrder;

	public SearchFieldsEntity() {
		
	}

	public SearchFieldsEntity(UUID id) {
		this.id = id;
		
	}

	public SearchFieldsEntity(String id) {
		this.id = UUID.fromString(id);
			
	}

	/*
	 * UUID, Name, Service Number (UM,DM,NM..+5 digit), Service Type, Base URL,
	 * IsActive
	 */

}
