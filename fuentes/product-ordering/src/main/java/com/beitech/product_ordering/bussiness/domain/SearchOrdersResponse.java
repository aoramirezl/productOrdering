package com.beitech.product_ordering.bussiness.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchOrdersResponse {

	private List<SearchOrdersOrderResponse> orders;
	
	public SearchOrdersResponse() {
		this.orders = new ArrayList<SearchOrdersOrderResponse>();
	}

	public void addOrder(SearchOrdersOrderResponse order) {
		this.orders.add(order);
	}
	
	public List<SearchOrdersOrderResponse> getOrders() {
		return orders;
	}

	public void setOrders(List<SearchOrdersOrderResponse> orders) {
		this.orders = orders;
	}
}
