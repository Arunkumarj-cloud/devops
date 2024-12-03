package com.oasys.uppcl_user__master_management.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PlanMasterDTO;
import com.oasys.uppcl_user__master_management.service.PlanMasterService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/planMaster")
@Log4j2
public class PlanMasterController {

	@Autowired
	PlanMasterService planMasterService;

//	@PreAuthorize(PrivilegeConstant.ADD_PLAN)
	@PostMapping("/create")
	public BaseDTO addPlan(@Valid @RequestBody PlanMasterDTO planMasterDTO) {
		//log.info("<---- PlanMasterController.addPlan() ----> STARTED");
		BaseDTO response = planMasterService.addPlan(planMasterDTO);
		//log.info("<---- PlanMasterController.addPlan() ----> ENDED");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@ApiOperation(value = "Get All Plans")
	@GetMapping("/getAllPlans")
	public BaseDTO getAllUser() {
		//log.info("<== Started PlanMasterController.getAllUser ==>");
		BaseDTO allUser = planMasterService.getAllPlans();
		//log.info("<== Ended PlanMasterController.getAllUser ==>");
		return allUser;
	}
	
//	@PreAuthorize("#oauth2.hasScope('Admin')")
    @ApiOperation(value = "Delete Plan with an ID", response = BaseDTO.class)
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDeleteById(@PathVariable UUID id) {
		//log.info("<== Started PlanMasterController.DeleteById ==>");
		BaseDTO userManagementDTO = planMasterService.softDeletePlan(id);
		//log.info("<== Ended PlanMasterController.DeleteById ==>");
		return userManagementDTO;
	}

//	@PreAuthorize("#oauth2.hasScope('Admin')")
	@ApiOperation(value = "Delete Plan with an ID", response = BaseDTO.class)
	@DeleteMapping("/delete/{id}")
	public BaseDTO DeleteById(@PathVariable UUID id) {
		//log.info("<== Started PlanMasterController.DeleteById ==>");
		BaseDTO userManagementDTO = planMasterService.deletePlan(id);
		//log.info("<== Ended PlanMasterController.DeleteById ==>");
		return userManagementDTO;
	}

//	@PreAuthorize(PrivilegeConstant.UPDATE_PLAN)
	@ApiOperation(value = "Update Plan with an ID", response = BaseDTO.class)
	@PutMapping("/update/{id}")
	public BaseDTO updateUser(@PathVariable UUID id, @Valid @RequestBody PlanMasterDTO planMasterDTO) {
		//log.info("<== Started PlanMasterController.updateUser ==>");
		BaseDTO userManagementDTO = planMasterService.updatePlan(id, planMasterDTO);
		//log.info("<== Ended PlanMasterController.updateUser ==>");
		return userManagementDTO;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
    @GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started PlanMasterController.getUserById ==>");
		BaseDTO response = planMasterService.getById(id);
		//log.info("<== Ended PlanMasterController.getUserById ==>");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//log.info("<-- PlanMasterController.getLazyList()---> Started");
		response = planMasterService.getLazyList(requestData);
		//log.info("<-- PlanMasterController.getLazyList()---> Ended");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/defaultPlan")
	public BaseDTO getDefaultPlan() {
		BaseDTO response = new BaseDTO();
		//log.info("<-- PlanMasterController.getDefaultPlan()---> Started");
		response = planMasterService.getDefaultPlan();
		//log.info("<-- PlanMasterController.getDefaultPlan()---> Ended");
		return response;
	}
	//@PreAuthorize("#oauth2.hasScope('Admin')")
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/findbyplan/{name}")
	public BaseDTO findbyPlanUUID(@PathVariable("name") String name) {
		BaseDTO response = new BaseDTO();
		response = planMasterService.findbyPlanUUID(name);
		return response;
	}
	
	@ApiOperation(value = "API to check if a plan exist by given id", response = BaseDTO.class)
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/exist")
	public Boolean isPlanExistById(@RequestParam(value="planId", required=true) UUID planId) {
		return planMasterService.isPlanExistById(planId);
	}
	
	@ApiOperation(value = "Api to get all active plans listing", response = BaseDTO.class)
//	@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/all/active")
	public BaseDTO getAllActive() {
		return  planMasterService.getAllActive();
	}
	
}
