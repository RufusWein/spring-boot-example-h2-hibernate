package com.example.springboot.prices.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.Data;

@Data
@javax.persistence.Entity
@javax.persistence.Table(name="PRICES")
public class Price {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private int id;
	
    @javax.persistence.Column(name = "BRAND_ID")
	private int brandId;

	@javax.persistence.Column(name="START_DATE")
	private String startDate;

	@javax.persistence.Column(name="END_DATE")
	private String endDate;
	
	@javax.persistence.Column(name="PRICE_LIST")
	private int priceList;
	
	@javax.persistence.Column(name="PRODUCT_ID")
	private int productId;
	
	private boolean priority;
	
	private float price;
	
	private String curr;

}