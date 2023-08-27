package com.example.springboot.prices.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

//import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QueryRequest {

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date appDate;
	
	@javax.validation.constraints.Min(1)
	private Integer productId;
	
	@javax.validation.constraints.Min(1)
	private Integer brandId;

}
