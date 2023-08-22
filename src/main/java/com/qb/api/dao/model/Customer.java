package com.qb.api.dao.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

	@MongoId
	private String id;
	private String compnayName;
	private String legalName;
	private String legalAddress;
	private String email;
	private String website;
	private BusinessAddress businessAddress;
	private TaxIdentifier taxIdentifier;
	private ValidationStatusEnum validationStatus;

}
