package com.beitech.product_ordering.bussiness.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.beitech.product_ordering.bussiness.service.Customer;

@RepositoryRestResource
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	Customer findOne(int idCliente);
}
