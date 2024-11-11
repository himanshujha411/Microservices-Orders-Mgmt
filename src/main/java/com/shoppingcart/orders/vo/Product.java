package com.shoppingcart.orders.vo;

import java.io.Serializable;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item")
public class Product implements Serializable {

	private static final long serialVersionUID = 5186013952828648626L;

	@Id
	//@Column(name = "Id")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	
	@Column(name="category")
	private String category;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String name;
	
	@NotNull(message="Please provide some price")
	@Min(value = 100, message = "Minimum value should be greater than 100")
	@Column(name = "price")
	private double price;
	
	@Column(name = "unit")
	private String unit;

	@Transient
	private MultipartFile productImage;

	// Getters and Setter

	
	
	public int getProductId() {
		return productId;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}

	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	
	public Product() {

	}

	public Product(int productId, String category, String description, String manufacturer,
			@NotEmpty(message = "Product Name is mandatory") String name,
			@NotNull(message = "Please provide some price") @Min(value = 100, message = "Minimum value should be greater than 100") double price,
			String unit, MultipartFile productImage) {
		super();
		this.productId = productId;
		this.category = category;
		this.description = description;
		this.manufacturer = manufacturer;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.productImage = productImage;
	}

}