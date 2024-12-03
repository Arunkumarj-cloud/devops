package com.oasys.feign.config;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.http.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.oasys.exception.FeignLayerExcecutionException;
import com.oasys.feign.dto.FeignAbstractDTO;
import com.oasys.clients.userservice.utils.CommonUtil;

import feign.FeignException;
import feign.codec.DecodeException;
import feign.codec.Decoder;

public class FeignInternalAPIDecoder implements Decoder {

	@Autowired
	private CommonUtil commonUtil;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object decode(feign.Response response, Type arg1) throws IOException, DecodeException, FeignException {

		if (response.body() == null) {
			return new Exception(response.reason() + ":" + response.status());
		}
		String resp = IOUtils.toString(response.body().asInputStream());
		try {
			Object object = commonUtil.jsonToObject(resp, (Class) arg1);

			if (object instanceof FeignAbstractDTO) {
				FeignAbstractDTO feignAbstractDTO = (FeignAbstractDTO) object;
				if (response.status() == HttpStatus.SC_OK && (feignAbstractDTO.getStatusCode() == HttpStatus.SC_OK || feignAbstractDTO.getStatusCode() == HttpStatus.SC_NO_CONTENT)) {
					return feignAbstractDTO;

				}

				if (feignAbstractDTO.getStatusCode() == null || feignAbstractDTO.getMessage() == null) {
					return new FeignLayerExcecutionException(response.request().url(), resp, response.status());
				}
				throw new FeignLayerExcecutionException(response.request().url(), resp, feignAbstractDTO);
			}
			if (response.status() == HttpStatus.SC_OK) {
				return object;
			}
			throw new FeignLayerExcecutionException(response.request().url(), resp, response.status());
		} catch (Exception ex) {
			if (ex instanceof FeignLayerExcecutionException) {
				throw ex;
			}
			System.out.println(ex.getMessage());
			throw new FeignLayerExcecutionException(response.request().url(), ex);
		}
	}

}
