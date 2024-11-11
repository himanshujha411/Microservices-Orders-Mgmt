package com.shoppingcart.orders.bo;

import com.shoppingcart.orders.vo.Customer;

public interface CustomerOrderBO {

	int insertCustomerOrder(Customer customer, int cartId);

}
