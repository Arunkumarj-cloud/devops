package com.oasys.feign.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.Constants;
import com.oasys.exception.FeignLayerExcecutionException;
import com.oasys.feign.dto.CBaseDTO;
import com.oasys.feign.dto.FeignAbstractDTO;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Autowired
	CommonUtil commonUtil;

	@Override
	public Exception decode(String methodKey, Response response) {

		try {
			if (response.body() == null) {
				return new FeignLayerExcecutionException(response.request().toString(), response.reason(),
						response.status());
			}
			String resp = IOUtils.toString(response.body().asInputStream());
			FeignAbstractDTO feignAbstractDTO = commonUtil.jsonToObject(resp, CBaseDTO.class);
			if (feignAbstractDTO.getStatusCode() == Constants.SUCCESS.getCode()) {
				return new FeignLayerExcecutionException(response.request().toString(), resp, response.status());
			}
			return new FeignLayerExcecutionException(response.request().toString(), resp, feignAbstractDTO);
		} catch (Exception e) {
			return new FeignLayerExcecutionException(methodKey, e);
		}

	}

}
