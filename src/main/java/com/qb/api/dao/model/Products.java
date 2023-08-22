package com.qb.api.dao.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Products {
 
	@MongoId
	private String id;
	private String name;
	private String validationUrl;

}
