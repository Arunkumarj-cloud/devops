package com.oasys.uppcl_user__master_management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;

import com.oasys.uppcl_user__master_management.entity.PancardRejectionReasonEntity;
import com.oasys.uppcl_user__master_management.repository.PancardRejectionRepository;
import com.oasys.uppcl_user__master_management.repository.PlanCountRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;



@Service
public class PlanCountService {
	@Autowired(required=true)
	PancardRejectionRepository pancardRejectionRepository;
	@Autowired(required=true)
	PlanCountRepository planCountRepository;
	@Autowired(required=true)
	ServiceRepository serviceRepository;
	
	
	public Map<String, Integer> getPlanCount() {
		Map<String, Integer> count = new HashMap<String, Integer>();
		// log.info("<-- PlanCountService.getPlanCount()
		// Started --> ");
		try {
			int planCount = planCountRepository.getPlanCountByActiveSttus();

			count.put("planCount", planCount);

		} catch (Exception e) {
//			log.error("<---- PlanCountService.getPlanCount() ----> EXCEPTION"+ e.getMessage());
		}
		// log.info("<-- PlanCountService.getPlanCount()
		// Ended -->");
		return count;
	}

	public Map<String, Integer> getServiceCount() {
		Map<String, Integer> count = new HashMap<String, Integer>();
		// log.info("<-- PlanCountService.getServiceCount()
		// Started --> ");
		try {
			int serviceCount = serviceRepository.getServiceCount();

			count.put("serviceCount", serviceCount);

		} catch (Exception e) {
	//		log.error("<---- PlanCountService.getAgentCountByApplicationId() ----> EXCEPTION"+ e.getMessage());
		}
		// log.info("<-- PlanCountService.getServiceCount()
		// Ended -->");
		return count;
	}

	
	public BaseDTO getRejectionPanCardList() {
		BaseDTO baseDTO=new BaseDTO();
		List<PancardRejectionReasonEntity> getpancardlist=new ArrayList<PancardRejectionReasonEntity>();
		try {
			getpancardlist=pancardRejectionRepository.getRejectionPanCardList();
			if(getpancardlist!=null && getpancardlist.size()>0) {
				baseDTO.setResponseContents(getpancardlist);
				baseDTO.setMessage("Record Fetched Successfully.");
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
	//		log.error("<---- PlanCountService.getRejectionPanCardList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		return baseDTO;
	}

}
