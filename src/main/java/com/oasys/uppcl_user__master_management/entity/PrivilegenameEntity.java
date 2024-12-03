package com.oasys.uppcl_user__master_management.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oasys.config.Trackable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "privilege")
@Getter
@Setter
//@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class PrivilegenameEntity extends Trackable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9193111483662932471L;

	@Id
	@Column(name = "privilege_name")
	@EqualsAndHashCode.Include
	private String privilegeName;

	@Column(name = "display_name")
	private String displayName;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "parent_id")\
	@Column(name = "parent_id")
	@JsonIgnore
	private String parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<PrivilegeEntity> children = new HashSet<>();

	public PrivilegenameEntity() {
	}

	public PrivilegenameEntity(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public PrivilegenameEntity(String privilegeName, String displayName, Set<PrivilegeEntity> children) {
		super();
		this.privilegeName = privilegeName;
		this.displayName = displayName;
		this.children = children;
	}

	public PrivilegenameEntity(String privilegeName, String displayName) {
		super();
		this.privilegeName = privilegeName;
		this.displayName = displayName;
	}

}
