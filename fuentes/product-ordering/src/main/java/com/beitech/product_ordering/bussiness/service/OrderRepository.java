package com.beitech.product_ordering.bussiness.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.beitech.product_ordering.bussiness.service.Order;

@RepositoryRestResource
public interface OrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByCreationDate(@Param("creation_date") String creationDate);
}