package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Data
@JsonIgnoreProperties
public class Commission {
	 private String createdDate;
	    private String createdBy;
	    private String modifiedDate;
	    private UUID globalCommissionId;
	 	private String hierarchyType;
	 	private UUID serviceId;
	 	private UUID serviceProviderId;
	 	private UUID subCategoryId;
	 	private String serviceName;
	 	private String serviceProviderName;
	 	private String serviceCategoryName;
	 	private String subCategoryName;
	 	private String subCategoryConstantName;
	 	private String slot;
	 	private UUID slabId;
	 	private Double maxCommisionValue;
	 	private Double adminActualCommisionValue;
	 	private Double ddCommisionValue;
	 	private Double pcdCommisionValue;
	 	private Double rCommisionValue;
	 	private String chargingType;
	    private String serviceCharge;
	 	private String gstType;
	 	private boolean tdsApplicable;
	 	private String settlementPeriod;
	 	private String offerName;
	 	private String offerDesc;
	 	private String adminRemarks;
	 	private Double rcommisionValue;
	 	private String uniqId;
	 	private Double adminCommisionValue;
	 	private Double asdrCommsionCharges;
	 	private Double actualDDCommisionValue;
		private Double actualPCDCommisionValue;
		private Double actualRCommisionValue;
		private Double tdsDDCommisionValue;
		private Double tdsPCDCommisionValue;
		private Double tdsRCommisionValue;
}
