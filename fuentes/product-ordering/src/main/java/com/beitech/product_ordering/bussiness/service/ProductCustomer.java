package com.beitech.product_ordering.bussiness.service;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product_customer database table.
 * 
 */
@Entity
@Table(name="product_customer")
@NamedQuery(name="ProductCustomer.findAll", query="SELECT p FROM ProductCustomer p")
public class ProductCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductCustomerPK id;

	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="productCustomer")
	private List<OrderDetail> orderDetails;

	public ProductCustomer() {
	}

	public ProductCustomerPK getId() {
		return this.id;
	}

	public void setId(ProductCustomerPK id) {
		this.id = id;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setProductCustomer(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setProductCustomer(null);

		return orderDetail;
	}

}