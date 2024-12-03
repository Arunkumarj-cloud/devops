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
@Table(name = "role")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class RoleMasterEntity extends Trackable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9129175042778865798L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "role_name",length = 25)
	private String roleName;
	
	@Column(name = "description",length = 100)
	private String description;
	
	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "user_role_name",length = 25)
	private String userRoleName;
	
	private Boolean status;

	public RoleMasterEntity() {}
	
	public RoleMasterEntity(String id) {
		this.id = UUID.fromString(id);
	}
	
	public RoleMasterEntity(UUID id) {
		this.id = id;
	}

}

