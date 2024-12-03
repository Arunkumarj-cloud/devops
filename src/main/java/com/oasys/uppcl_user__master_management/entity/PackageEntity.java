package com.oasys.uppcl_user__master_management.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oasys.config.Trackable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
//@JsonIgnoreProperties(ignoreUnknown = true)


@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "package_master")
public class PackageEntity extends Trackable {



	/**
	* 
	*/
	private static final long serialVersionUID = -2802869654224602894L;
	@JsonAnySetter
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 16)
	// @JsonProperty("id")
	private UUID id;

	@Column(name="name")
	private String name;
	
	// @JsonProperty("amount")
	@Column(name="amount")
	private Double amount;
	
	@Column(name="status")
	private Boolean status;
	
	@Column(name="is_default_package")
	private Boolean isDefaultPackage;
	
//	 @JsonProperty
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "package_service_category_mapping",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "service_category_id"))
    private Set<ServiceCategoryEntity> servceCategoryList = new HashSet<>();
	
	@Column(name="remarks")
	private String remarks;
	
	

	 
}


