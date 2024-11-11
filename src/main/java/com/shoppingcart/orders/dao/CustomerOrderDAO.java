package com.shoppingcart.orders.dao;

import com.shoppingcart.orders.vo.Customer;

public interface CustomerOrderDAO {

	int insertCustomerOrder(Customer customer, int CartId);

}
