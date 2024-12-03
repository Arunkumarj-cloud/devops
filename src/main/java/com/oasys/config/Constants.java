package com.oasys.config;

import java.util.regex.Pattern;

import com.oasys.config.Constants.Constant;

public class Constants {
	public static class Constant {
		private Integer constant;

		public Integer getCode() {
			return constant;
		}

		public Constant(Integer constant) {
			this.constant = constant;
		}
	}

	//primary flag for proof
	public static final Constant PAN_CARD_UPLOAD = new Constant(1);
	public static final Constant PHOTO_OF_OUTLET = new Constant(2);
	public static final Constant ADDRESS_PROOF = new Constant(3);
	public static final Constant OTHER_IDENTITY_PROOF = new Constant(4);
	public static final Constant SHOP_ADDRESS_PROOF = new Constant(5);
	public static final Constant PROFILE_PICTURE = new Constant(6);

	

	
	//flag of calculation configuration
	public static final Constant GST = new Constant(1);
	public static final Constant TDS_IT_rule = new Constant(2);
	public static final Constant TDS_GST_rule = new Constant(3);
	public static final Constant INCENTIVE_AMOUNT = new Constant(4);
	public static final Constant TOTAL_BILL_AMOUNT = new Constant(5);
	public static final Constant MERCHANT_INCENTIVE = new Constant(6);
	
	// primaryflag
		public static final Constant TRANSACTIONTYPE = new Constant(1);

		public static final Constant TRANSACTIONMODE = new Constant(2);

		public static final Constant STATUS = new Constant(3);
		
		public static final Constant DATE = new Constant(4);
		
		public static final Constant TRANSACTIONDESCRIPTION=new Constant(5);
		
			
		public static final Constant DEPOSITTYPE=new Constant(7);
		

		// Secondary

		public static final Constant FUNDSDEPOSIT = new Constant(1);
		public static final Constant WALLETRECHARGE = new Constant(2);
		public static final Constant WALLETINCENTIVE = new Constant(3);
		public static final Constant WITHDRAWAL = new Constant(4);
		public static final Constant FUNDTRANSFER = new Constant(5);
		public static final Constant BILLPAY = new Constant(15);
		public static final Constant SUCCESS = new Constant(20);
		public static final Constant FAIL = new Constant(21);
		public static final Constant PENDING = new Constant(70);
		
		public static final Constant NORESPONSE = new Constant(58);
		
		

		public static final Constant ACCOUNT = new Constant(6);
		public static final Constant WALLET = new Constant(7);
		public static final Constant CASH = new Constant(8);
		public static final Constant ONLINE = new Constant(9);

		public static final Constant NEW = new Constant(10);
		public static final Constant INPROCESS = new Constant(11);
		public static final Constant ONHOLD = new Constant(12);
		public static final Constant APPROVED = new Constant(13);
		public static final Constant REJECTED = new Constant(14);
		
		public static final Constant PENDINGATFINANCEEND=new Constant(32);
		public static final Constant REJECTEDFROMFINANCEEND=new Constant(33);
		public static final Constant APPROVEDFROMFINANCEEND=new Constant(34);
		public static final Constant CREDIT=new Constant(35);
		public static final Constant WALLETTOPUPRECHARGE=new Constant(36);
		public static final Constant WALLETCREATION=new Constant(37);
		public static final Constant ELECTRICITYBILLCHARGES=new Constant(38);
		public static final Constant INCENTIVEAMOUNT=new Constant(39);
		public static final Constant TDSAMOUNT=new Constant(40);
		public static final Constant DEBIT=new Constant(41);
		
		
		
		
		
		public static final Constant DCASH=new Constant(46);
		public static final Constant NEFT=new Constant(47);
		public static final Constant RTGS=new Constant(48);
		public static final Constant IMPS=new Constant(49);
		public static final Constant CHEQUE=new Constant(50);
		public static final Constant OTHERS=new Constant(51);
		public static final Constant PRINTRECEIPTCHARGES=new Constant(57);
		
		public static final Constant INCENTIVETOWALLET = new Constant(71);
		
		
		// error code
		public static final String RESPONSE_CODE = "Respons Code";

		public static final String BHIM_AADHAR_PAY = "Bhim AadhaarPay";

		public static final String RECEIPT_REQUIRED = "Receipt Required";

		public static final String STATUS_ = "status";
		

		// secondaryflag

		/*
		 * public static final Constant WALLETRECHARGE = new Constant(2); public static
		 * final Constant WALLETINCENTIVE = new Constant(3); public static final
		 * Constant WITHDRAWAL= new Constant(4); public static final Constant
		 * FUNDTRANSFER= new Constant(5);
		 * 
		 * public static final Constant ACCOUNT = new Constant(2); public static final
		 * Constant WALLET = new Constant(2); public static final Constant CASH = new
		 * Constant(2); public static final Constant ONLINE = new Constant(2);
		 * 
		 * public static final Constant NEW = new Constant(3); public static final
		 * Constant INPROCESS = new Constant(3); public static final Constant ONHOLD =
		 * new Constant(3); public static final Constant APPROVED = new Constant(3);
		 * public static final Constant REJECTED = new Constant(3);
		 */

		public static final String ExtraParameters = "NA";
		public static final String Success = "Success";
		public static final String Failure = "Failure";
		public static final String SuccessResponse = "00";
		public static final String SuccessResponseBbps = "00";

		public static final String Sha56 = "abcdefghijklmop";
		public static final String Aes128AadharPay = "i5C3Eft6X8";
		public static final String Aes128Aeps = "d7JZf2i6KT";

		public static final String AadharUserId = "1000002624";
		public static final String AepsUserId = "1000002723";

		public static final String IciciTerminalId = "OS000001";

		public static final String UniqueHostDeviceCode = "ICI0000OS000001";

		public static final String UpiMerchantId = "YES0000000012142";

		public static final String True = "true";
		public static final String False = "false";
		public static final String PINCODE = "pincode";
		public static final String ID = "id";
		public static final String STATUS_PARAM = "status";
		public final static Pattern UUID_REGEX_PATTERN = Pattern
				.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");
		public static final String CREATED_DATE = "createdDate";
		public static final String ASC = "ASC";
		public static final Object SEARCH_BY_DATE = "searchByDate";
		public static final String MODIFIED_DATE = "modifiedDate";
		public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		public static final String START_TIME = "00:00:00";
		public static final String END_TIME = "23:59:59";
		public static final String SPACE = " ";
		public static final String PERCENTAGE = "%";
		public static final String FROM_OR_TO_DATE = "fromDate or toDate";
		public static final String DATE_FRMT = "yyyy-MM-dd";
		public final static Pattern PINCODE_REGEX = Pattern
				.compile("^[1-9][0-9]{5}");
		public static final String PLAN_ID = "planId";
		public static final String SERVICE_ID = "serviceId";
		public static final String SERVICE_IDS = "serviceIds";
		public static final String DESC = "DESC";
		public static final String PLAN_NAME = "planName";
		public static final String NAME = "name";
		public static final String DEFAULT = "Default";
		public static final String AMOUNT = "amount";
		public static final String SERVICE_CATEGORY_ENTITY = "serviceCategoryEntity";
		public static final String SERVICE_CATEGORY_ID = "serviceCategoryId";
		public static final String SERVICE_CATEGORY_NAME = "serviceCategoryName";
		public static final String PACKAGE_NAME = "packageName";
		public static final String PACKAGE = "package";
		public static final String SERVICE_CATEGORY_IDS = "serviceCategoryIds";
		public static final String ACTION_DATE = "actionDateTime";
		public static final String SERVICE_NAME = "serviceName";
		public static final String REMARKS = "remarks";
		public static final String ACTION_TYPE= "actionType";
		public static final int NAME_FIELD_MAX_LENTH= 50;
		public static final String PROJECT_TYPE = "Project Type";
		public static final String SUPER_DISTRIBUTOR = "Super Distributor";
		public static final String DISTRIBUTOR = "Distributor";
		public static final String INDIVIDUAL = "Individual";
		public static final String ROLE_ID = "roleId";
		public static final String USER_TYPE_ID = "userTypeId";
		public static final String BLANK = "";
		public static final int REMARKS_FIELD_MAX_LENTH= 100;
		public static final int NAME_FIELD_MIN_LENTH= 3;
		public static final String PREVIOUS_AMOUNT = "previousAmount";
		public static final String UPDATED_AMOUNT = "updatedAmount";
		public static final int TEXT_FIELD_MIN_LENGTH= 3;
		public static final String ROLE_NAME = "roleName";
		public static final String DESCRIPTION = "description";
		public static final String PAN_NUMBER = "PAN Number";
		public static final String PHONE_NUMBER = "Phone Number";
		public final static Pattern PHONE_NUMBER_PATTERN = Pattern
				.compile("\\d{10}");
		public static final String UNDERSCORE = "_";
		
		public final static Pattern PAN_NUMBER_PATTERN = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
		
		
		

}
