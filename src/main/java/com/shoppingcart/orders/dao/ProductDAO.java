package com.shoppingcart.orders.dao;

import java.util.List;

import com.shoppingcart.orders.vo.Product;

public interface ProductDAO {

	List<Product> getAllProduct();

	Product getProductById(int productId);

}
