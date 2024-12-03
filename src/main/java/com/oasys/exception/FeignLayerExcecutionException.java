package com.oasys.exception;

import com.oasys.feign.dto.FeignAbstractDTO;

public class FeignLayerExcecutionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3598859628828600953L;
	private FeignAbstractDTO baseDTO;

	public FeignLayerExcecutionException(String url, String body, int status) {
		super(String.format("Error during call Url:-%s , status:-%s, body:-%s", url, status, body));
	}

	public FeignLayerExcecutionException(String url, String resp, FeignAbstractDTO baseDTO) {
		super(String.format("Error during call Url:-%s , body:-%s", url, resp));
		this.baseDTO = baseDTO;
	}

	public FeignLayerExcecutionException(String url, Throwable th) {
		super(String.format("Error during call Url:-%s", url), th);
	}

	public FeignAbstractDTO getBaseDTO() {
		return baseDTO;
	}

}
