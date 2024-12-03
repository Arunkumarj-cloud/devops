package com.oasys.constant;

public interface PrivilegeConstant {
	
	
	public static final String VIEW_PRIVILEGE="read_privilege";
	public static final String CRUD_PRIVILEGE="write_privilege";
	public static final String VIEW_ROLE="read_role";
	public static final String CRUD_ROLE="write_role";
	public static final String VIEW_USER="read_user";
	public static final String CRUD_USER="write_user";
	public static final String VIEW_ALL_API="isAuthenticated()";
	public static final String USER_PERMISSIONS="#oauth2.hasScope('USER_PERMISSIONS') or #oauth2.hasScope('Admin') ";
	public static final String ADD_EDUCATION="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_EDUCATION')";
	public static final String UPDATE_EDUCATION="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_EDUCATION')";

	public static final String ADD_PLAN="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_PLAN')";
	public static final String UPDATE_PLAN="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_PLAN')";
	
	public static final String ADD_MARITAL_STATUS="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_MARITAL_STATUS')";
	public static final String UPDATE_MARITAL_STATUS="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_MARITAL_STATUS')";

	public static final String ADD_SERVICE_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SERVICE_TYPE')";
	public static final String UPDATE_SERVICE_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SERVICE_TYPE')";

	public static final String ADD_ADDRESS_PROOF="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_ADDRESS_PROOF')";
	public static final String UPDATE_ADDRESS_PROOF="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_ADDRESS_PROOF')";

	public static final String ADD_TERMS_CONDITION="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_TERMS_CONDITION')";
	public static final String UPDATE_TERMS_CONDITION="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_TERMS_CONDITION')";

	public static final String ADD_BANK_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_BANK_TYPE')";
	public static final String UPDATE_BANK_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_BANK_TYPE')";
	
	public static final String ADD_PROOF_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_PROOF_TYPE')";
	public static final String UPDATE_PROOF_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_PROOF_TYPE')";

	public static final String ADD_FAQ_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_FAQ_CATEGORY')";
	public static final String UPDATE_FAQ_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_FAQ_CATEGORY')";
	
	public static final String ADD_SLAB="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SLAB')";
	public static final String UPDATE_SLAB="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SLAB')";
	
	public static final String ADD_STATE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_STATE')";
	public static final String UPDATE_STATE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_STATE')";
	
	public static final String ADD_BANK_PROOF="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_BANK_PROOF')";
	public static final String UPDATE_BANK_PROOF="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_BANK_PROOF')";
	
	public static final String ADD_DISTRICT="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_DISTRICT')";
	public static final String UPDATE_DISTRICT="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_DISTRICT')";
	
	public static final String ADD_FAQ_LIST="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_FAQ_LIST')";
	public static final String UPDATE_FAQ_LIST="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_FAQ_LIST')";
	
	public static final String ADD_BANK_BRANCH="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_BANK_BRANCH')";
	public static final String UPDATE_BANK_BRANCH="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_BANK_BRANCH')";
	
	public static final String ADD_TYPE_OF_ACCOUNT="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_TYPE_OF_ACCOUNT')";

	public static final String UPDATE_TYPE_OF_ACCOUNT="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_TYPE_OF_ACCOUNT')";
	
	public static final String ADD_SERVICE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SERVICE')";
	public static final String UPDATE_SERVICE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SERVICE')";
	
	public static final String ADD_NATURE_OF_BUSINESS="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_NATURE_OF_BUSINESS')";
	public static final String UPDATE_NATURE_OF_BUSINESS="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_NATURE_OF_BUSINESS')";
	
	public static final String ADD_GENDER="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_GENDER')";
	public static final String UPDATE_GENDER="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_GENDER')";
	
	public static final String ADD_REASON_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_REASON_TYPE')";
	public static final String UPDATE_REASON_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_REASON_TYPE')";
	
	public static final String ADD_RELIGION="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_RELIGION')";
	public static final String UPDATE_RELIGION="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_RELIGION')";
	
	public static final String ADD_SUB_SERVICE_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SUB_SERVICE_CATEGORY')";
	public static final String UPDATE_SUB_SERVICE_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SUB_SERVICE_CATEGORY')";
	
	public static final String ADD_BANK="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_BANK')";
	public static final String UPDATE_BANK="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_BANK')";
	
	public static final String ADD_SERVICE_PROVIDER="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SERVICE_PROVIDER')";
	public static final String UPDATE_SERVICE_PROVIDER="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SERVICE_PROVIDER')";
	
	public static final String ADD_PRIVACY_POLICY="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_PRIVACY_POLICY')";
	public static final String UPDATE_PRIVACY_POLICY="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_PRIVACY_POLICY')";
	
	public static final String ADD_USER_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_USER_TYPE')";
	public static final String UPDATE_USER_TYPE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_USER_TYPE')";
	
	public static final String ADD_PINCODE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_PINCODE')";
	public static final String UPDATE_PINCODE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_PINCODE')";
	
	public static final String ADD_USER_ROLE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_USER_ROLE')";
	public static final String UPDATE_USER_ROLE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_USER_ROLE')";
	
	public static final String ADD_SERVICE_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_SERVICE_CATEGORY')";
	public static final String UPDATE_SERVICE_CATEGORY="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_SERVICE_CATEGORY')";
	
	public static final String ADD_NOTIFICATION_TEMPLATE="#oauth2.hasScope('Admin') or #oauth2.hasScope('ADD_NOTIFICATION_TEMPLATE')";
	public static final String UPDATE_NOTIFICATION_TEMPLATE="#oauth2.hasScope('Admin') or #oauth2.hasScope('UPDATE_NOTIFICATION_TEMPLATE')";
	
	public static final String ADD_GOLD_LOAN_PINCODE="#oauth2.hasScope('ADD_GOLD_LOAN_PINCODE')";
	public static final String UPDATE_GOLD_LOAN_PINCODE="#oauth2.hasScope('UPDATE_GOLD_LOAN_PINCODE')";
	public static final String VIEW_GOLD_LOAN_PINCODE="#oauth2.hasScope('VIEW_GOLD_LOAN_PINCODE')";
	
	public static final String ADD_PLAN_SERVICES_MAPPING="#oauth2.hasScope('ADD_PLAN_SERVICES_MAPPING')";
	public static final String UPDATE_PLAN_SERVICES_MAPPING="#oauth2.hasScope('UPDATE_PLAN_SERVICES_MAPPING')";
	public static final String VIEW_PLAN_SERVICES_MAPPING_BY_PLAN_ID="#oauth2.hasScope('VIEW_PLAN_SERVICES_MAPPING_BY_PLAN_ID')";
	public static final String DELETE_PLAN_SERVICES_MAPPING="#oauth2.hasScope('DELETE_PLAN_SERVICES_MAPPING')";
	public static final String VIEW_ALL_PLAN_SERVICES_MAPPING = "#oauth2.hasScope('VIEW_ALL_PLAN_SERVICES_MAPPING')";
	public static final String UPDATE_SERVICE_FEE_MAPPING="#oauth2.hasScope('UPDATE_SERVICE_FEE_MAPPING')";
	public static final String VIEW_SERVICE_FEE_MAPPING="#oauth2.hasScope('VIEW_SERVICE_FEE_MAPPING')";
	public static final String SERVICE_FEE_MAPPING_LIST="#oauth2.hasScope('SERVICE_FEE_MAPPING_LIST')";
	public static final String ADD_SERVICE_FEE_MAPPING="#oauth2.hasScope('ADD_SERVICE_FEE_MAPPING')";
	
	public static final String ADD_PACKAGE_SERVICE_MAPPING="#oauth2.hasScope('ADD_PACKAGE_SERVICE_MAPPING')";
	public static final String EDIT_PACKAGE_SERVICE_MAPPING="#oauth2.hasScope('EDIT_PACKAGE_SERVICE_MAPPING')";
	public static final String PACKAGE_SERVICE_MAPPING_LIST="#oauth2.hasScope('PACKAGE_SERVICE_MAPPING_LIST')";
	public static final String VIEW_PACKAGE_SERVICE_MAPPING="#oauth2.hasScope('VIEW_PACKAGE_SERVICE_MAPPING')";

	public static final String VIEW_PACKAGE_VERSION_HISTORY="#oauth2.hasScope('VIEW_PACKAGE_VERSION_HISTORY')";

	public static final String ADD_PROJECT_TYPE="#oauth2.hasScope('ADD_PROJECT_TYPE')";
	public static final String UPDATE_PROJECT_TYPE="#oauth2.hasScope('UPDATE_PROJECT_TYPE')";
	public static final String VIEW_PROJECT_TYPE="#oauth2.hasScope('VIEW_PROJECT_TYPE')";
	public static final String PROJECT_TYPE_LIST="#oauth2.hasScope('PROJECT_TYPE_LIST')";
	
	public static final String ADD_BLACKLIST_PAN="#oauth2.hasScope('ADD_BLACKLIST_PAN')  or  #oauth2.hasScope('Admin')";
	public static final String UPDATE_BLACKLIST_PAN ="#oauth2.hasScope('UPDATE_BLACKLIST_PAN')  or  #oauth2.hasScope('Admin')";
	public static final String VIEW_BLACKLIST_PAN="#oauth2.hasScope('VIEW_BLACKLIST_PAN')  or  #oauth2.hasScope('Admin')";
	public static final String VIEW_LIST_BLACKLIST_PAN="#oauth2.hasScope('VIEW_LIST_BLACKLIST_PAN')  or  #oauth2.hasScope('Admin')";
	
	
	public static final String ADD_BLACKLIST_PHONE="#oauth2.hasScope('ADD_BLACKLIST_PHONE') or  #oauth2.hasScope('Admin')";
	public static final String UPDATE_BLACKLIST_PHONE ="#oauth2.hasScope('UPDATE_BLACKLIST_PHONE') or #oauth2.hasScope('Admin')";
	public static final String VIEW_BLACKLIST_PHONE="#oauth2.hasScope('VIEW_BLACKLIST_PHONE')  or  #oauth2.hasScope('Admin')";
	public static final String VIEW_LIST_BLACKLIST_PHONE="#oauth2.hasScope('VIEW_LIST_BLACKLIST_PHONE')  or  #oauth2.hasScope('Admin')";
	
	public static final String VIEW_HIERARCHY_NAME = " #oauth2.hasScope('VIEW_HIERARCHY_NAME') or #oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('Customer Support Officer') or #oauth2.hasScope('Finance Officer')  or #oauth2.hasScope('Merchant') or #oauth2.hasScope('TenantAdmin') or #oauth2.hasScope('Third Party') ";
	public static final String VIEW_SERVICE_FEE_CONFIG_VERSION_HISTORY="#oauth2.hasScope('VIEW_SERVICE_FEE_CONFIG_VERSION_HISTORY')";

	public static final String SO_ROLE = "Sales Officer";
	public static final String CO_ADMIN_ROLE = "CO Manager";
	public static final String FO_ADMIN_ROLE = "FO Manager";
	
}
