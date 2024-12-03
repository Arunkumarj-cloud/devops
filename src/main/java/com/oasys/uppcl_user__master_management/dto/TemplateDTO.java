package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TemplateDTO {

	private UUID id;

	@NotBlank
	@Size(min=3,message="Please Enter Minimum 3 Characters ")
	private String name;

//	@NotBlank
	@Size(min=3,message="Please Enter Minimum 3 Characters ")
	private String type;

	private String templateContent;
	private String application_name;

//	@NotNull
	private UUID appId;
	
//	@NotBlank
	private String notificationNetwork;
	
	@NotNull
	private Boolean status;
	
	private String notificationChannel;

	private String temp_type;
	private String remarks;

	
	private UUID applicationId;
	
	private Date  createdDate;
	private Date  modifiedDate;
	private String applicationName;
	private SubCategoryEntity subServiceId;
	private ServiceCategoryEntity serviceId;
	
	private String template_id;
	private String templateId;

	private String templateType;
//	SimpleDateFormat templateID = new SimpleDateFormat("yyyyMMddHHmmss");
//    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
 
}
