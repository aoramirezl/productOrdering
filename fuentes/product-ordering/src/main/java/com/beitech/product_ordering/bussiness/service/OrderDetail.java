package com.beitech.product_ordering.bussiness.service;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_detail database table.
 * 
 */
@Entity
@Table(name="order_detail")
@NamedQuery(name="OrderDetail.findAll", query="SELECT o FROM OrderDetail o")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderDetailPK id;

	@Column(name="order_detail_price")
	private int orderDetailPrice;

	@Column(name="product_quantity")
	private int productQuantity;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="order_id", insertable = false, updatable = false)
	private Order order;

	//bi-directional many-to-one association to ProductCustomer
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="customer_id", referencedColumnName="customer_id", insertable = false, updatable = false),
		@JoinColumn(name="product_id", referencedColumnName="product_id", insertable = false, updatable = false)
		})
	private ProductCustomer productCustomer;

	public OrderDetail() {
	}

	public OrderDetailPK getId() {
		return this.id;
	}

	public void setId(OrderDetailPK id) {
		this.id = id;
	}

	public int getOrderDetailPrice() {
		return this.orderDetailPrice;
	}

	public void setOrderDetailPrice(int orderDetailPrice) {
		this.orderDetailPrice = orderDetailPrice;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductCustomer getProductCustomer() {
		return this.productCustomer;
	}

	public void setProductCustomer(ProductCustomer productCustomer) {
		this.productCustomer = productCustomer;
	}

}