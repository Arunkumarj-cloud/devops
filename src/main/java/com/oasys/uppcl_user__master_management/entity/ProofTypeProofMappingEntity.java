package com.oasys.uppcl_user__master_management.entity;



import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Data
@Table(name = "proof_type_proof_mapping")
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProofTypeProofMappingEntity extends Trackable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "proof_type_id", referencedColumnName = "uuid")
	private ProofTypeMasterEntity proofTypeId;
	
	@ManyToOne
	@JoinColumn(name = "proof_id", referencedColumnName = "uuid")
	private ProofMasterEntity proofId;
	
	private Boolean status;
	
	public ProofTypeProofMappingEntity() {
	}

	  public ProofTypeProofMappingEntity(String id) 
	  {
		  this.id =
	  UUID.fromString(id);
		  }
	  
	  public ProofTypeProofMappingEntity(UUID uuid)
	  {
		  this.id = uuid;
		  }
	 
	
	
}
