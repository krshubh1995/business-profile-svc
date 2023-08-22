package com.qb.api.dao.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qb.api.validations.CreateRecord;
import com.qb.api.validations.UpdateRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class CustomerValidate {

	@MongoId
	@JsonProperty("tempId")
	private String id;
	@NotBlank(groups = UpdateRecord.class, message = "Please provide customerId")
	private String customerId;
	@NotBlank(groups = CreateRecord.class, message = "Please provide companyName")
	private String compnayName;
	@NotBlank(groups = CreateRecord.class, message = "Please provide companyName")
	private String legalName;
	@NotBlank(groups = CreateRecord.class, message = "Please provide companyName")
	private String legalAddress;
	@NotBlank(groups = CreateRecord.class, message = "Please provide companyName")
	private String email;
	@NotBlank(groups = CreateRecord.class, message = "Please provide companyName")
	private String website;
	@NotNull(groups = CreateRecord.class, message ="please provide businessAddress")
	private BusinessAddress businessAddress;
	@NotNull(groups = CreateRecord.class, message ="please provide taxIdentifier")
	private TaxIdentifier taxIdentifier;
	private SqlOperations sqlOperation;
	private List<ProductsValidationAudit> validatedProductsError = new ArrayList<>();
	private ValidationStatusEnum validationStatus;

}
