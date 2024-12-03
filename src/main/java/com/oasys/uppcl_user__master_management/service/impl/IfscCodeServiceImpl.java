package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.MessageConstant;
import com.oasys.uppcl_user__master_management.dto.IfscDTO;
import com.oasys.uppcl_user__master_management.entity.BranchEntity;
import com.oasys.uppcl_user__master_management.repository.BranchRepository;
import com.oasys.uppcl_user__master_management.service.IfscCodeService;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class IfscCodeServiceImpl implements IfscCodeService  {

	
	@Autowired
	BranchRepository branchrepository;
	
	/*@Autowired
	BankDataRepository bankdataRepository;
	
	@Autowired
	StateDetailsRepository statedetailsrepository;
	
	@Autowired
	IfscRepository ifscRepository;
	*/
	
	@Override
	public BaseDTO ifsccodedata(IfscDTO walletDto) {
		String ifsc = null;
		BaseDTO response = new BaseDTO();

		List<BranchEntity> listresponse = null;
		try {
			ifsc = walletDto.getIfscCode();
		
         if(ifsc!=null) {
				ifsc=ifsc.toUpperCase();
			}
		
			//log.info("ifcc" + ifsc);
			listresponse = branchrepository.getBankid(ifsc);
			if (listresponse.size() <= 0) {
				//log.info("<------Empty data in IfscDataRepository.ifscdata() ----->");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			    response.setMessage(MessageConstant.nullRecord);
				return response;
			}
			if (listresponse != null && listresponse.size() > 0) {
				//log.info("<------IfscDataRepository.ifscdata() ----->");
				
				response.setResponseContents(listresponse);
				response.setMessage("IFSC Data Retreived Successfully");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}
		} catch (Exception e) {
			log.error("error in ifscdata ===>", e);
			response.setStatusCode(ErrorDescription.INTERNAL_SERVER_ERROR.getCode());
		}
		return response;
	}


}
