package com.oasys.config;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileUploadResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** e@Datarror code. 0 if success else unique error code value */

	@Override
	public String toString() {
		return "GenericResponse [status=" + status + ", errorCode="
				+ errorCode + ", userDisplayMesg=" + userDisplayMesg + ", unProcesseData=" + unProcesseData + "]";
	}

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getUserDisplayMesg() {
		return userDisplayMesg;
	}

	public void setUserDisplayMesg(String userDisplayMesg) {
		this.userDisplayMesg = userDisplayMesg;
	}

	private int errorCode;
	private String userDisplayMesg;
	private Object unProcesseData;
}
