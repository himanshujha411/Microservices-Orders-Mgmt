package com.shoppingcart.orders.bo;

import com.shoppingcart.orders.vo.User;

public interface UserBO {

	User findUserByUsername(String username);
	
	
}
