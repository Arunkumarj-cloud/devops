package com.oasys.uppcl_user__master_management.entity;

import java.io.Serializable;


import lombok.Data;
@Data
public class GenderResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private Boolean status;

}
