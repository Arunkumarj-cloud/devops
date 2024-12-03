package com.oasys.uppcl_user__master_management.response;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
@Data
public class GstResponse {
private UUID id;
	
	private UUID applicationId;
	
	private String requestDate;
	
	private String criteria;
		
	private double amount;
	
	private String transactionId;
	
	private Integer processStatus;
	
	private Integer active;
	
	private String ip;
	
	private String processDate;
	
	private String remarks;
	
	private String file;
	
	private Integer status;
	
	private UUID createdBy;
	
	private String applicationName;
	
	private String cretaedName;
	
	private double previousAmount;
	
	private String createdDate;
	
	private String modifiedDate;
	
	private String processType;
	
	
	private UUID modifiedBy;
	private Date startDate;
	private Date endDate;
	private String updatedby;
	
	private Integer settingstype;
	
	private String agentid;
	private String userrole;
	private String username;
	
	private String userid;
	private String completedDate;
	
}
