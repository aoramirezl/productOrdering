package com.beitech.product_ordering.bussiness.service;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the product_customer database table.
 * 
 */
@Embeddable
public class ProductCustomerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="customer_id", insertable=false, updatable=false)
	private int customerId;

	@Column(name="product_id", insertable=false, updatable=false)
	private int productId;

	public ProductCustomerPK() {
	}
	public int getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductCustomerPK)) {
			return false;
		}
		ProductCustomerPK castOther = (ProductCustomerPK)other;
		return 
			(this.customerId == castOther.customerId)
			&& (this.productId == castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.customerId;
		hash = hash * prime + this.productId;
		
		return hash;
	}
}