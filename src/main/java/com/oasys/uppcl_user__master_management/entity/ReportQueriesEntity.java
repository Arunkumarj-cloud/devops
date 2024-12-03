package com.oasys.uppcl_user__master_management.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.Trackable;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name ="report_queries")
@JsonIgnoreProperties({ "hibernatelazyinitializer", "handler" })
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Audited
public class ReportQueriesEntity extends Trackable {
	
	private static final long serialVersionUID = 1L;
	
		/**
		 * unique id of Allotment
		 */
		@Id
		@GeneratedValue(generator = "uuid2")
		@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
		@Column(name = "id", length = 16)
		UUID id;
		
		/** report name*/
		@Column(name="report_name")
		String reportName;
		 
		/** dataQuery*/
		@Column(name="data_query", columnDefinition = "text")
	    String dataQuery;
		 
		 /* summaryQuery */
		@Column(name="summary_query", columnDefinition = "text")
		String summaryQuery;
		
		/*  description */
		@Column(name="description", columnDefinition = "text")
		String description;	
		
		
		@Column(name="primary_flag",columnDefinition = "bigint" )
		Integer primaryFlag;	
		


}
