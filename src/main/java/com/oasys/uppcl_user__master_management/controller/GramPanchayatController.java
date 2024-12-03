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
import com.oasys.uppcl_user__master_management.dto.GramPanchayatDTO;
import com.oasys.uppcl_user__master_management.service.GramPanchayatService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/panchayat")
@Log4j2
public class GramPanchayatController {
	@Autowired
	GramPanchayatService  gramPanchayatService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO careateGramPanchayat(@Valid @RequestBody GramPanchayatDTO  gramPanchayatDTO) {
		//log.info(" <====Started GramPanchayatController.careateGramPanchayat=====>");
		BaseDTO response=new BaseDTO();
		response = gramPanchayatService.create(gramPanchayatDTO);
		//log.info(" <====Ended GramPanchayatController.careateGramPanchayat=====>");
		return response;
		
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getAll")
	public BaseDTO getGramPanchayat() {
		//log.info("<===Started GramPanchayatController.getGramPanchayat ===>");
		BaseDTO response= gramPanchayatService.getGramPanchayat();
		//log.info("<===Ended GramPanchayatController.getGramPanchayat ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/get/{id}")
	public BaseDTO getGramPanchayatById(@PathVariable UUID id) {
		//log.info("<===Started GramPanchayatController.getGramPanchayatById ===>");
		BaseDTO response= gramPanchayatService.getGramPanchayatById(id);
		//log.info("<===Ended GramPanchayatController.getGramPanchayatById ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO updateGramPanchayat(@PathVariable UUID id, @Valid @RequestBody GramPanchayatDTO gramPanchayatDTO) {
		//log.info("<===Started GramPanchayatController.updateGramPanchayat ===>");
		BaseDTO response= gramPanchayatService.updateGramPanchayat(id,gramPanchayatDTO);
		//log.info("<===Ended GramPanchayatController.updateGramPanchayat ===>");
		return response;	
		}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteGramPanchayat(@PathVariable UUID id) {
		//log.info("<===Started GramPanchayatController.deleteGramPanchayat ===>");
		BaseDTO response= gramPanchayatService.deleteGramPanchayat(id);
		//log.info("<===Ended GramPanchayatController.deleteGramPanchayat ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/lazylist")
	public BaseDTO getAllPanchayatlazy(@RequestBody PaginationRequestDTO pageData) {
		//log.info("<== Started PanchayatController.getAllPanchayatlazy() ==>");
		BaseDTO response = gramPanchayatService.getAllPanchayatlazy(pageData);
		//log.info("<== Ended PanchayatController.getAllPanchayatlazy()  ==>");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@GetMapping("/getByBlock/{id}")
	public BaseDTO getByBlockId(@PathVariable UUID id) {
		//log.info("<===Started GramPanchayatController.getByBlockId ===>");
		BaseDTO response= gramPanchayatService.getByBlockId(id);
		//log.info("<===Ended GramPanchayatController.getByBlockId ===>");
		return response;	
		}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- Started GramPanchayatController  softDelete  STARTED --->");
		BaseDTO baseDTO = gramPanchayatService.softDelete(id);
		//log.info("<---  GramPanchayatController  softDelete  END --->");
		return baseDTO;
	}

}
