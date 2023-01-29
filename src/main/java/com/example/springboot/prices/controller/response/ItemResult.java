package com.example.springboot.prices.controller.response;

import lombok.Data;

@Data
public class ItemResult {
	
	Integer productId;

	Integer brandId;
	
	Integer priceList;
	
	String startDate;
	
	String endDate;
	
	Float price;

}
