package com.shoppingcart.orders.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.orders.bo.CustomerOrderBO;
import com.shoppingcart.orders.dao.CustomerOrderDAO;
import com.shoppingcart.orders.vo.Customer;

@Service
public class CustomerOrderBOImpl implements CustomerOrderBO{

	@Autowired
	CustomerOrderDAO custOrderDAO;
	
	@Override
	public int insertCustomerOrder(Customer customer, int cartId)
	{
		return custOrderDAO.insertCustomerOrder(customer, cartId);
	}
}
