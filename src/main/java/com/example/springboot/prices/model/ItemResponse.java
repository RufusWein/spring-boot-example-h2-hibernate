package com.example.springboot.prices.model;

import lombok.Data;

@Data
public class ItemResponse {
	
	Integer productId;

	Integer brandId;
	
	Integer priceList;
	
	String startDate;
	
	String endDate;
	
	Float price;

}
