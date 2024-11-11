package com.shoppingcart.orders.dao;

import java.util.List;

import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.CartItem;
import com.shoppingcart.orders.vo.Product;

public interface CartItemDAO {

	List<CartItem> getCartItemByCartId(int cartId);

	int updateCartItem(CartItem cartItem, double price, int quantity);

	int insertCartItem(Product product, Cart cart);

	CartItem getCartItemByCartIdAndProductId(int cartId, int productId);

	int deleteCartItem(Product product, Cart cart);

	int deleteCartItemAfterOrderSubmit(int cartId);

}
