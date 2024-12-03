package com.oasys.uppcl_user__master_management.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.oasys.config.Trackable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role_privilege_mapping")
@Getter
@Setter
@ToString
public class RolePrivilegeEntity extends Trackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2597957183918797049L;

	@EmbeddedId
    private RolePrivilegePK rolePrivilegePK;

    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private RoleMasterEntity role;

    @ManyToOne
    @MapsId("privilege_name")
    @JoinColumn(name = "privilege_name")
    private PrivilegeEntity privilege;   
    

}
