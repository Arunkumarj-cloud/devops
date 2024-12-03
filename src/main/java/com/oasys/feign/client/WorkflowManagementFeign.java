package com.oasys.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oasys.feign.config.InternalFeginAPIConfig;
import com.oasys.feign.dto.WorkflowBaseDTO;
import com.oasys.uppcl_user__master_management.dto.WorkflowProjectDetailsDTO;

@Component

@FeignClient(name = "WorkflowManagementFeign", url = "${workflow.management.host}", configuration = InternalFeginAPIConfig.class)
public interface WorkflowManagementFeign {

	@PostMapping(value = "/workflowDetails/create-project")
	public WorkflowBaseDTO updateWorkFlowDetails(@RequestBody WorkflowProjectDetailsDTO workflowProjectDetailsDTO);

}
