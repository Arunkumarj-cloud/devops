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
import com.oasys.uppcl_user__master_management.dto.BlockDTO;
import com.oasys.uppcl_user__master_management.service.BlockService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/block")
@Log4j2
public class BlockController {
	@Autowired
	BlockService blockService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody BlockDTO blockDTO) {
		//log.info("<--- Block create Controller STARTED --->");
		BaseDTO response = blockService.create(blockDTO);
		//log.info("<--- Block create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- Block getAll Controller STARTED --->");
		BaseDTO response = blockService.getAll();
		//log.info("<--- Block getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- Block getAll Controller STARTED --->");
		BaseDTO response = blockService.getAllActive();
		//log.info("<--- Block getAll Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody BlockDTO blockDTO) {
		//log.info("<--- Block update Controller STARTED --->");
		BaseDTO response = blockService.update(id,blockDTO);
		//log.info("<--- Block update Controller END --->");
		return response;
	}
		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazylist")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- Block getById Controller STARTED --->");
	response = blockService.getLazyList(requestData);
	//log.info("<--- Block getById Controller END --->");
	return response;
	}
	

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- Block getById Controller STARTED --->");
		BaseDTO response = blockService.getById(id);
		//log.info("<--- Block getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO delete(@PathVariable("id") UUID id) {
		//log.info("<--- Block delete Controller STARTED --->");
		BaseDTO response = blockService.delete(id);
		//log.info("<--- Block delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Block delete Controller STARTED --->");
		BaseDTO response = blockService.softDelete(id);
		//log.info("<--- Block delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getByDistrictId/{id}")
	public BaseDTO getByDistrictId(@PathVariable("id") UUID id) {
		//log.info("<--- Block getByDistrictId Controller STARTED --->");
		BaseDTO response = blockService.getByDistrictId(id);
		//log.info("<--- Block getByDistrictId Controller END --->");
		return response;
	}

}
