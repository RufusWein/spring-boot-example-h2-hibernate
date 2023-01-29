package com.example.springboot.prices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="PRICES")
public class Price {

	@Id
	@GeneratedValue
	private int id;
	
    @Column(name = "BRAND_ID")
	private int brandId;

	@Column(name="START_DATE")
	private String startDate;

	@Column(name="END_DATE")
	private String endDate;
	
	@Column(name="PRICE_LIST")
	private int priceList;
	
	@Column(name="PRODUCT_ID")
	private int productId;
	
	private boolean priority;
	
	private float price;
	
	private String curr;

}