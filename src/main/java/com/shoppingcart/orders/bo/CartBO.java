package com.shoppingcart.orders.bo;

import com.shoppingcart.orders.vo.Cart;

public interface CartBO {

	int insertCart(int customerId);

	Cart findCartByCustomerId(int customerId);

	int updateCartTotalPrice(int customerId, double totalPrice);

	int updateCartPrice(int customerId);

}
