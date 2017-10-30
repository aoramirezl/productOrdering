package com.beitech.product_ordering.bussiness.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchOrdersOrderResponse {
	
	private Date creationDate;
	private int orderId;
	private int total;
	private String deliveryAdress;
	private List<SearchOrdersProductResponse> products;
	
	public SearchOrdersOrderResponse() {
		products = new ArrayList<SearchOrdersProductResponse>();
	}
	
	public void addProduct(SearchOrdersProductResponse product) {
		this.products.add(product);
	}
	
	public void addProduct(int quantity, String productName) {
		this.products.add(new SearchOrdersProductResponse(quantity, productName));
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getDeliveryAdress() {
		return deliveryAdress;
	}
	public void setDeliveryAdress(String deliveryAdress) {
		this.deliveryAdress = deliveryAdress;
	}
	public List<SearchOrdersProductResponse> getProducts() {
		return products;
	}
	public void setProducts(List<SearchOrdersProductResponse> products) {
		this.products = products;
	}
}
