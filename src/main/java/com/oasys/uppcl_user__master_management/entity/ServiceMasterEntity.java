package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service")
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@Audited
public class ServiceMasterEntity extends Trackable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "service_name", nullable = false, length = 25)
	private String serviceName;
	
	@Column(name = "service_number", nullable = false, length = 10)
	private String serviceNumber;
	
	//
	@Column(name = "service_mode" , nullable = false, length = 10)
	private String serviceMode;

	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "base_url", nullable = false, length = 100)
	private String baseURL;
	
	@ManyToOne
	@JoinColumn(name = "service_type_id", referencedColumnName = "id" , nullable = false)
	private ServiceTypeMasterEntity serviceTypeId;
	
	public ServiceMasterEntity() {
		
	}
	
	public ServiceMasterEntity(UUID id) {
		this.id = id;
		
	}
		
	public ServiceMasterEntity(String id) {
		this.id = UUID.fromString(id);
			
	}
	
	/*
	 * UUID, Name, Service Number (UM,DM,NM..+5 digit), Service Type, Base URL,
	 * IsActive
	 */

}
