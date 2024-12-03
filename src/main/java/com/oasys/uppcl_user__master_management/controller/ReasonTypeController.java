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
import com.oasys.uppcl_user__master_management.dto.ReasonTypeDTO;
import com.oasys.uppcl_user__master_management.service.ReasonTypeService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reasonType")
@Log4j2
public class ReasonTypeController {
	
	@Autowired
     ReasonTypeService reasonTypeService;
	
	//@PreAuthorize(PrivilegeConstant.ADD_REASON_TYPE)
	@PostMapping("/create")
	public BaseDTO create(@Valid @RequestBody ReasonTypeDTO reasonTypeDTO) {
		//log.info("<--- ReasonType create Controller STARTED --->");
		BaseDTO baseDTO = reasonTypeService.create(reasonTypeDTO);
		//log.info("<--- reasonTypeService create Controller END --->");
		return baseDTO;
	}
	
	
	  // @PreAuthorize(PrivilegeConstant.UPDATE_REASON_TYPE)
	  
	  @PutMapping("/update/{id}")
	  public BaseDTO update(@PathVariable("id") UUID id,@Valid @RequestBody ReasonTypeDTO reasonTypeDTO) 
	  {
	  //log.info("<--- ReasonType update Controller STARTED --->"); 
		  BaseDTO baseDTO = reasonTypeService.update(id,reasonTypeDTO);
	  //log.info("<--- ProofTypeProofMapping update Controller END --->"); 
		  return baseDTO;
	  }
	  
		
		  @GetMapping("/get/{id}")
		  public BaseDTO getById(@PathVariable("id") UUID id)
		  { //log.info("<--- ReasonType getById Controller STARTED --->"); 
			  BaseDTO baseDTO = reasonTypeService.getById(id);
		  //log.info("<--- ReasonType getById Controller END --->"); 
		  return baseDTO;
		  }
		  
		  //@PreAuthorize("#oauth2.hasScope('Admin')")
		  //@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
		  
		  @GetMapping("/getall") 
		  public BaseDTO getAll()
		  {
		  //log.info("<--- ReasonType getAll Controller STARTED --->");
			  BaseDTO baseDTO = reasonTypeService.getAll();
		  //log.info("<--- ReasonType getAll Controller END --->");
			  return baseDTO;
			  }
		  
		  //@PreAuthorize("#oauth2.hasScope('Admin')")
		  //@PreAuthorize(PrivilegeConstant.VIEW_ALL_API) 
		  @GetMapping("/getallactive")
		  public BaseDTO getAllActive() 
		  {
		  //log.info("<--- ReasonType getAll Controller STARTED --->"); 
		 BaseDTO baseDTO= reasonTypeService.getAllActive();
		  //log.info("<--- ReasonType getAll Controller END --->");
	      return baseDTO;
			  }
		  
		  
		  @PostMapping("/lazylist")
		  public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
		  //log.info("<--- ReasonType lazylist Controller STARTED --->");
			  BaseDTO baseDTO = reasonTypeService.getLazyList(requestData);
		  //log.info("<--- ReasonType lazylist Controller END --->"); 
			  return baseDTO; 
			  }
		  
		 // @PreAuthorize("#oauth2.hasScope('Admin')")		  
		  @DeleteMapping("/delete/{id}")
		  public BaseDTO delete(@PathVariable("id") UUID id)
		  { //log.info("<--- ReasonType delete Controller STARTED --->");
			  BaseDTO  baseDTO = reasonTypeService.delete(id);
		  //log.info("<--- ReasonType delete Controller END --->");
			  return baseDTO;
			  }
		  
		  
		  // @PreAuthorize("#oauth2.hasScope('Admin')")
		  @PutMapping("/softdelete/{id}") 
		  public BaseDTO softDelete(@PathVariable("id") UUID id)
		  { //log.info("<--- ReasonType softDelete Controller STARTED --->");
		  BaseDTO baseDTO = reasonTypeService.softDelete(id);
		  //log.info("<--- ReasonType softDelete Controller END --->");
		  return baseDTO;
		  }
		  
		 

}
