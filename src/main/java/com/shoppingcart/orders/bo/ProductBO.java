package com.shoppingcart.orders.bo;

import java.util.List;

import com.shoppingcart.orders.vo.Product;

public interface ProductBO {

	List<Product> getallproducts();

	Product getProductById(int productId);

}
