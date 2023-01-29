package com.example.springboot.prices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.prices.controller.request.QueryRequest;
import com.example.springboot.prices.controller.response.ItemResult;
import com.example.springboot.prices.service.PricesService;

import jakarta.validation.Valid;

@Validated
@RestController
public class PricesController {
    
    @Autowired
    private PricesService service;
    
    /*
    Se pide:
    	 
    	Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:
    	 
    	Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
    	Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
	*/
	@PostMapping("/")
	@ResponseBody
	public ResponseEntity<List<ItemResult>> query(@RequestBody @Valid QueryRequest queryRequest) {
		try {
			List<ItemResult> response = service.doQuery(
					queryRequest.getAppDate(), 
					queryRequest.getProductId(), 
					queryRequest.getBrandId());
			return ResponseEntity.ok(response);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();		
		}

	}
	
}
