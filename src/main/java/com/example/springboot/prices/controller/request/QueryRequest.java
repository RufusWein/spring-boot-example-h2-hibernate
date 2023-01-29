package com.example.springboot.prices.controller.request;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QueryRequest {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private String appDate;
	
	@Min(1)
	private Integer productId;
	
	@Min(1)
	private Integer brandId;

}
