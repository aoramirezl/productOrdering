package com.beitech.product_ordering.bussiness.service;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the order_detail database table.
 * 
 */
@Embeddable
public class OrderDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="order_id", insertable=false, updatable=false)
	private int orderId;

	@Column(name="product_id", insertable=false, updatable=false)
	private int productId;

	@Column(name="customer_id", insertable=false, updatable=false)
	private int customerId;

	public OrderDetailPK() {
	}
	public int getOrderId() {
		return this.orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrderDetailPK)) {
			return false;
		}
		OrderDetailPK castOther = (OrderDetailPK)other;
		return 
			(this.orderId == castOther.orderId)
			&& (this.productId == castOther.productId)
			&& (this.customerId == castOther.customerId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderId;
		hash = hash * prime + this.productId;
		hash = hash * prime + this.customerId;
		
		return hash;
	}
}