package com.qb.api.dao.model;

import com.qb.api.validations.CreateRecord;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessAddress {

	private String line1;
	private String line2;
	@NotBlank(groups = CreateRecord.class, message = "Please provide city")
	private String city;
	@NotBlank(groups = CreateRecord.class, message = "Please provide state")
	private String state;
	@NotBlank(groups = CreateRecord.class, message = "Please provide zip")
	private String zip;
	@NotBlank(groups = CreateRecord.class, message = "Please provide country")
	private String country;

}
