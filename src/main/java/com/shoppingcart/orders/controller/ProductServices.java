package com.shoppingcart.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.orders.bo.ProductBO;
import com.shoppingcart.orders.util.ServiceConstants;
import com.shoppingcart.orders.vo.Product;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_ProductService)
public class ProductServices {
	
	final static Logger LOG = LoggerFactory.getLogger(ProductServices.class);
	
	@Autowired
	ProductBO productBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_GetAllProducts, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request)
	{
		List<Product> products = null;
		try
		{
			products = productBO.getallproducts();
			if(products==null)
			{
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_GetProductById, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductById(@RequestParam("productId") Integer productId, HttpServletRequest request)
	{
		Product products = null;
		try
		{
			products = productBO.getProductById(productId);
			if(products==null)
			{
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<Product>(products, HttpStatus.OK);
	}

}
