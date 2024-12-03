package com.oasys.config;

import com.oasys.config.ErrorDescription.Error;

public interface ErrorDescription {
	public static class Error {
		private Integer errorCode;
		

		public Integer getErrorCode() {
			return errorCode;
		}

		public Integer getCode() {
			return errorCode;
		}

		public Error(Integer errorCode) {
			this.errorCode = errorCode;
		}
	}

	public static final Error SUCCESS_RESPONSE = new Error(200);
	public static final Error CREATED = new Error(201);
	public static final Error NON_AUTHORITATIVE_INFORMATION = new Error(203);
	public static final Error EMPTY_DATA = new Error(204);
	public static final Error BAD_REQUEST = new Error(400);
	public static final Error AUTHENTICATION_FAILED = new Error(401);
	public static final Error NOT_FOUND = new Error(404);
	public static final Error METHOD_NOT_ALLOWED = new Error(405);
	public static final Error FAILURE_RESPONSE = new Error(417);
	public static final Error INTERNAL_SERVER_ERROR = new Error(500);
	public static final Error ALREADY_EXISTS = new Error(409);
	public static final Error START_RANGE_NOT_GRETER = new Error(410);
	public static final Error NOT_ACCEPTABLE = new Error(406);


}
