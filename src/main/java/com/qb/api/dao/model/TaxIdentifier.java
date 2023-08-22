package com.qb.api.dao.model;

import com.qb.api.validations.CreateRecord;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxIdentifier {

	@NotBlank(groups = CreateRecord.class, message = "Please provide pan")
	private String pan;
	@NotBlank(groups = CreateRecord.class, message = "Please provide ein")
	private String ein;

}
