package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "language")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LanguageMasterEntity extends Trackable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "language_name", nullable = false, length = 100)
	private String languageName;
	
	@Column(name = "code", nullable = false, length = 10)
	private String code;

	@Column(name = "status" , nullable = false)
	private Boolean status;
	
	public LanguageMasterEntity() {
		
	}
	public LanguageMasterEntity(UUID id) {
		this.id = id;		
	}
	public LanguageMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}


}
