package com.qb.api.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.ValidationStatusEnum;

public interface CustomersValidateRepository extends MongoRepository<CustomerValidate, String> {

	 List<CustomerValidate> findAllByValidationStatusIn(List<ValidationStatusEnum> validationStatus);
	
	

}
