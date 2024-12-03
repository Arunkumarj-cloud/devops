package com.oasys.uppcl_user__master_management.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="faq")	    
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({ "hibernatelazyinitializer", "handler" })
public class FaqEntity extends Trackable  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@Column(name="question")
	String question;
	
	@Column(name = "answer")
	String answer;
	
	@Column(name = "language")
	String language;
	@Column(name = "htmlcontent")
	String htmlcontent;

	@Column(name = "image")
	String image;
	@Column(name = "remarks")
	String remarks;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id",nullable = false, foreignKey = @ForeignKey(name = "faq_faqcategory_FK"))
	private FaqCategoryEntity categoryId;
	
	@Column(name="status")
	Boolean status;
}
