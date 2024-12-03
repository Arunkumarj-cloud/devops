package com.oasys.uppcl_user__master_management.controller;

import java.util.List;
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
import com.oasys.uppcl_user__master_management.dto.ProofTypeMasterDTO;
import com.oasys.uppcl_user__master_management.service.ProofTypeMasterService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/proofType")
@Log4j2
public class ProofTypeMasterController {

	@Autowired
	ProofTypeMasterService proofTypeService;
	
  // @PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getAll")
	public BaseDTO getAll()
	{
		log.info("<== Started ProofTypeController.getAll() ==>");
		BaseDTO baseDTO=new BaseDTO();
		baseDTO=proofTypeService.getAll();
		log.info("<== Ended ProofTypeController.getAll() ==>");
				return baseDTO;
				
	}
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ProofTypeMasterDTO proofTypeMasterDTO) {
		//log.info("<== Started ProofTypeController.create ==>");
		BaseDTO baseDTO = new BaseDTO();
		baseDTO = proofTypeService.create(proofTypeMasterDTO);
		//log.info("<== Ended ProofTypeController.create ==>");
		return baseDTO;
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable UUID id)
	{
		log.info("<== Started ProofTypeController.delete ==>");
		BaseDTO baseDTO=new BaseDTO();
		baseDTO=proofTypeService.delete(id);
		log.info("<== Ended ProofTypeController.delete ==>");
		return baseDTO;
	}
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable UUID id, @Valid @RequestBody ProofTypeMasterDTO proofTypeMasterDTO)
	{
		log.info("<== Started ProofTypeController.update ==>");
		BaseDTO baseDTO=new BaseDTO();
		baseDTO=proofTypeService.update(id,proofTypeMasterDTO);
		log.info("<== Ended ProofTypeController.update ==>");
		return baseDTO;
	}
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable UUID id) {
		//log.info("<== Started ProofTypeController.getById ==>");
		BaseDTO baseDTO = proofTypeService.getById(id);
		//log.info("<== Ended ProofTypeController.getById ==>");
		return baseDTO;
	}
	
	  @PostMapping("/lazylist")
	  public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
			BaseDTO baseDTO = new BaseDTO();
			//log.info("<== Started ProofTypeController.getLazyList ==>");
			baseDTO = proofTypeService.getLazyList(requestData);
			//log.info("<== Ended ProofTypeController.getLazyList ==>");
			return baseDTO;
		}

	 
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<== Started ProofTypeController.getAllActive ==>");
		BaseDTO baseDTO = proofTypeService.getAllActive();
		//log.info("<== Ended ProofTypeController.getAllActive ==>");
		return baseDTO;
	}
	
	@DeleteMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<== Started ProofTypeController.softDelete ==>");
		BaseDTO response = proofTypeService.softDelete(id);
		//log.info("<== Ended ProofTypeController.softDelete ==>");
		return response;
	}
	
	@GetMapping("/by-name")
	public BaseDTO findByProofTypeNames(@RequestParam(value = "proofTypeNames", required = true) List<String> proofTypeNames) {
		return proofTypeService.findByProofTypeNames(proofTypeNames);
	}	
	
	
	
	
	
}
