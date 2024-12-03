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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="city_master")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class CityMasterEntity extends Trackable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 16)
	private UUID id;
	
	@Column(name = "city_name", nullable = false, length = 25)
	private String cityName;
	
	@Column(name = "status", nullable = false)
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "state_id", referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "`city_master_ibfk_1"))
	private StateMasterEntity stateId;
	
	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id",nullable = false,foreignKey = @ForeignKey(name = "city_master_ibfk_2"))
	private DistrictMasterEntity districtId;
	
	public CityMasterEntity() {}
		
		public CityMasterEntity(String id) {
			this.id = UUID.fromString(id);
			
		}
		public CityMasterEntity(UUID id) {
			this.id = id;
			}

		
		
		
	
	
}
