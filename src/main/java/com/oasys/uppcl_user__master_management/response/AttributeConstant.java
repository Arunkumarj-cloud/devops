package com.oasys.uppcl_user__master_management.response;

import java.util.Locale;

import org.springframework.context.MessageSource;

public enum AttributeConstant {
	 STATE_ID("STATE_ID"),
	   DISTRICT_ID("DISTRICT_ID");


		private String attributeName;
		
		private AttributeConstant(String attributeName) {
			this.attributeName=attributeName;
		}
		private MessageSource messageSource;
		
		
		public void setMessageSource(MessageSource messageSource) {
			this.messageSource = messageSource;
		}
		public String getMessage() 
		{		
			return messageSource.getMessage(String.valueOf(attributeName),new Object[]{},Locale.ENGLISH);
		}
		public String getMessage(Object[] arg1) {
			return messageSource.getMessage(String.valueOf(attributeName), arg1, Locale.ENGLISH);
		}

	}
