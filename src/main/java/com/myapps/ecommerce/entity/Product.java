package com.myapps.ecommerce.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pId;
	private String productName;
	private String description;
	private String imgUrl;
	private double price;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<CartItem> cartItems;

	public Product() {
	}

	public Product(String productName, String description, String imgUrl, double price) {
		this.productName = productName;
		this.description = description;
		this.imgUrl = imgUrl;
		this.price = price;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
		return "Product [pId=" + pId + ", productName=" + productName + ", description=" + description + ", imgUrl="
				+ imgUrl + ", price=" + price + "]";
	}
}
