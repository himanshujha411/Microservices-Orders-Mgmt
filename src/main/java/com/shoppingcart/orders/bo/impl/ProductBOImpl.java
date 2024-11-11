package com.shoppingcart.orders.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.orders.bo.ProductBO;
import com.shoppingcart.orders.dao.ProductDAO;
import com.shoppingcart.orders.vo.Product;

@Service
public class ProductBOImpl implements ProductBO{

	@Autowired
	ProductDAO productDAO;
	
	@Override
	public List<Product> getallproducts()
	{
		return productDAO.getAllProduct();
	}
	
	@Override
	public Product getProductById(int productId)
	{
		return productDAO.getProductById(productId);
	}
}

