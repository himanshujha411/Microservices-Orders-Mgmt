package com.shoppingcart.orders.dao;

import com.shoppingcart.orders.vo.Cart;

public interface CartDAO {

	int insertCustomer(int customerId);

	Cart findCartByCustomerId(int customerId);

	int updateCartTotalPrice(int customerId, double totalPrice);

	int updateCartPrice(int customerId);

}
