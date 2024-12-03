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
@Table(name="education")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(callSuper=false)
public class EducationEntity extends Trackable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length =16)
	private UUID id;
	
	@Column(name="name",length = 25)
	private String name; 
	
	@Column(name="status")
	private Boolean status;
	
	
	@Column(name="remarks")
	private String remarks;
	

	public EducationEntity() {}
	
	public EducationEntity(UUID id) {
		this.id= id;
	}
	public EducationEntity(String id) {
		this.id = UUID.fromString(id);
	}


}



