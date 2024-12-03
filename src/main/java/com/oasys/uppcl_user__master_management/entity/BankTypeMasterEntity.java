package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name="bank_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(callSuper=false)
public class BankTypeMasterEntity extends Trackable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length =16)
	private UUID id;
	
	@Column(name="type",length = 25)
	private String type; 
	
	@Column(name="status")
	private Boolean status;
	

	public BankTypeMasterEntity() {}
	
	public BankTypeMasterEntity(UUID id) {
		this.id= id;
	}
	public BankTypeMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}

}
