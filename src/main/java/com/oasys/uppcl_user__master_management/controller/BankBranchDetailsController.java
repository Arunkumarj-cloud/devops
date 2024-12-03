package com.oasys.uppcl_user__master_management.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
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

import com.oasys.clients.userservice.utils.UserExcelExporter;
import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BankBranchDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;
import com.oasys.uppcl_user__master_management.service.BankBranchDetailsService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bankbranchdetails")
@Log4j2
public class BankBranchDetailsController {

	@Autowired
	BankBranchDetailsService  bankBranchDetailsService;
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PostMapping("/create")
	public BaseDTO createBankBranch(@Valid @RequestBody BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<--- BankBranch create Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.create(bankBranchDetailsDTO);
		//log.info("<--- BankBranch create Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getall")
	public BaseDTO getAll() {
		//log.info("<--- BankBranch getAll Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getAll();
		//log.info("<--- BankBranch getAll Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/getallactive")
	public BaseDTO getAllActive() {
		//log.info("<--- BankBranch getAll Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getAllActive();
		//log.info("<--- BankBranch getAll Controller END --->");
		return response;
	}
    
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/update/{id}")
	public BaseDTO updateBankBranch(@PathVariable("id") UUID id,@Valid @RequestBody BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<--- BankBranch update Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.update(id,bankBranchDetailsDTO);
		//log.info("<--- BankBranch update Controller END --->");
		return response;
	}
		
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazyListWithIfscCode")
	public BaseDTO getLazyList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- BankBranch lazylist Controller STARTED --->");
	response = bankBranchDetailsService.getLazyListWithIfscCode(requestData);
	//log.info("<--- BankBranch lazylist Controller END --->");
	return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@GetMapping("/get/{id}")
	public BaseDTO getById(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch getById Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getById(id);
		//log.info("<--- BankBranch getById Controller END --->");
		return response;
	}

	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@DeleteMapping("/delete/{id}")
	public BaseDTO deleteBankBranch(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch delete Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.delete(id);
		//log.info("<--- BankBranch delete Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	@PutMapping("/softdelete/{id}")
	public BaseDTO softDelete(@PathVariable("id") UUID id) {
		//log.info("<--- BankBranch softDelete Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.softDelete(id);
		//log.info("<--- BankBranch softDelete Controller END --->");
		return response;
	}
	
	/*@GetMapping("/getByBankNameId/{id}")
	public BaseDTO getByBankNameId(@PathVariable("id") UUID id) {
		log.info("<--- BankBranch getByBankNameId Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getByBankNameId(id);
		log.info("<--- BankBranch getByBankNameId Controller END --->");
		return response;
	}
	*/
    
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getBranchDetails")
	public BaseDTO getBankBranchDetails(@RequestBody BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<--- BankBranch getBranchDetails Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getBankBranchDetails(bankBranchDetailsDTO);
		//log.info("<--- BankBranch getBranchDetails Controller END --->");
		return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/lazyListWithBranchName")
	public BaseDTO getLazyListWithBranchName(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- BankBranch lazylist Controller STARTED --->");
	response = bankBranchDetailsService.getLazyListWithBranchName(requestData);
	//log.info("<--- BankBranch lazylist Controller END --->");
	return response;
	}
     
	//@PreAuthorize("#oauth2.hasScope('Admin') or #oauth2.hasScope('Sales Officer') or #oauth2.hasScope('TenantAdmin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/getBranchList")
	public BaseDTO getBranchList(@RequestBody PaginationRequestDTO requestData) {
	BaseDTO response = new BaseDTO();
	//log.info("<--- BankBranch getBranchList Controller STARTED --->");
	response = bankBranchDetailsService.getBranchList(requestData);
	//log.info("<--- BankBranch getBranchList Controller END --->");
	return response;
	}
	
	//@PreAuthorize("#oauth2.hasScope('Admin')")
	//@PreAuthorize(PrivilegeConstant.VIEW_ALL_API)
	@PostMapping("/ifsccode")
	public BaseDTO getBankdetailsByIfscCode(@RequestBody BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<-----BankBranchDetailsController.getBankdetailsByIfscCode()---->");
		BaseDTO response = bankBranchDetailsService.getBankdetailsByIfscCode(bankBranchDetailsDTO);
		//log.info("<-----BankBranchDetailsController.getBankdetailsByIfscCode()---->");
		return response;
		
	}
	@PostMapping("/getBankDetailsByIfscCode")
	public BaseDTO getBankDetailsByIfscCode(@RequestBody BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<--- BankBranch getBranchDetails Controller STARTED --->");
		BaseDTO response = bankBranchDetailsService.getBankBranchDetails(bankBranchDetailsDTO);
		//log.info("<--- BankBranch getBranchDetails Controller END --->");
		return response;
	}
	
	@GetMapping("/ifsc/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ifsc_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<BankBranchDetailsEntity> listUsers = bankBranchDetailsService.listAll();
         
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
         
        excelExporter.export(response);    
    } 

}
