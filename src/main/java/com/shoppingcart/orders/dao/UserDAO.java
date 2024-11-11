package com.shoppingcart.orders.dao;

import com.shoppingcart.orders.vo.User;

public interface UserDAO {

	User findUserByUsername(String username);
	boolean isUserEmailExist(String username);
	int insertUser(User user);
	
}
