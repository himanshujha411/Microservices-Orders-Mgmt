package com.shoppingcart.orders.vo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerorder")
public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = -6571020025726257848L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerOrderId;

	private int customerId;
	private int shippingAddressId;
	private int billingAddressId;
	
	private int cart_id;

	//@OneToOne
	/*
	 * @JoinColumn(name = "customerId") private Customer customer;
	 * 
	 * //@OneToOne
	 * 
	 * @JoinColumn(name = "shippingAddressId") private ShippingAddress
	 * shippingAddress;
	 * 
	 * //@OneToOne
	 * 
	 * @JoinColumn(name = "billingAddressId") private BillingAddress billingAddress;
	 */

	/*
	 * public BillingAddress getBillingAddress() { return billingAddress; }
	 * 
	 * public void setBillingAddress(BillingAddress billingAddress) {
	 * this.billingAddress = billingAddress; }
	 */

	public int getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(int customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(int shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public int getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(int billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	/*
	 * public Customer getCustomer() { return customer; }
	 * 
	 * public void setCustomer(Customer customer) { this.customer = customer; }
	 * 
	 * public ShippingAddress getShippingAddress() { return shippingAddress; }
	 * 
	 * public void setShippingAddress(ShippingAddress shippingAddress) {
	 * this.shippingAddress = shippingAddress; }
	 */

}