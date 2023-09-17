package com.myapps.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pId;
	private String productName;
	private String description;
	private String imgUrl;

//	@ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
//	private Set<Cart> carts = new HashSet<>();

	Product() {

	}

	public Product(Integer pId, String productName, String description, String imgUrl) {
		super();
		this.pId = pId;
		this.productName = productName;
		this.description = description;
		this.imgUrl = imgUrl;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
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

//	public Set<Cart> getCarts() {
//		return carts;
//	}
//
//	public void setCarts(Set<Cart> carts) {
//		this.carts = carts;
//	}
}
