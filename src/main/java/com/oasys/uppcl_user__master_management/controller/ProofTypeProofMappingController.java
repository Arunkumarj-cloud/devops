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
import org.springframework.web.bind.annotation.RestController;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.service.ProofTypeProofMappingService;

@RestController
@RequestMapping("/prooftypeproof")
public class ProofTypeProofMappingController {
     
	@Autowired
	ProofTypeProofMappingService proofTypeProofMappingService;
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info("<--- ProofTypeProofMapping create Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.create(proofTypeProofMappingDTO);
		//log.info("<--- ProofTypeProofMapping create Controller END --->");
		return baseDTO;
	}
	
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info("<--- ProofTypeProofMapping update Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.update(id,proofTypeProofMappingDTO);
		//log.info("<--- ProofTypeProofMapping update Controller END --->");
		return baseDTO;
	}
	
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- ProofTypeProofMapping getById Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.getById(id);
		//log.info("<--- ProofTypeProofMapping getById Controller END --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- ProofTypeProofMapping getAll Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.getAll();
		//log.info("<--- ProofTypeProofMapping getAll Controller END --->");
		return baseDTO;
	}
	
	
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- ProofTypeProofMapping getAll Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.getAllActive();
		//log.info("<--- ProofTypeProofMapping getAll Controller END --->");
		return baseDTO;
	}
	
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		//log.info("<--- ProofTypeProofMapping lazylist Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.getLazyList(requestData);
		//log.info("<--- ProofTypeProofMapping lazylist Controller END --->");
		return baseDTO;
	}
	
	
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- ProofTypeProofMapping delete Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.delete(id);
		//log.info("<--- ProofTypeProofMapping delete Controller END --->");
		return baseDTO;
	}
	
	
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- ProofTypeProofMapping softDelete Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.softDelete(id);
		//log.info("<--- ProofTypeProofMapping softDelete Controller END --->");
		return baseDTO;
	}
	
	
	
	@GetMapping("/getbyprooftype/{id}")
	public BaseDTO getByProofTypeId(@PathVariable("id") UUID id) {
		//log.info("<--- ProofTypeProofMapping getByUserTypeId Controller STARTED --->");
		BaseDTO baseDTO = proofTypeProofMappingService.getByProofTypeId(id);
		//log.info("<--- ProofTypeProofMapping getByUserTypeId Controller END --->");
		return baseDTO;
	}
	
	
}
