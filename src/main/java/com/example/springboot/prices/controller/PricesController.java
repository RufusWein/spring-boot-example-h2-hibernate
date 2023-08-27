package com.example.springboot.prices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.prices.model.ItemResponse;
import com.example.springboot.prices.model.QueryRequest;
import com.example.springboot.prices.service.PricesService;

//import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1")
//@PreAuthorize("hasAuthority('ROLE_client')")
public class PricesController {
    
    @Autowired
    private PricesService service;
    
    /*
    Se pide:
    	 
    	Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:
    	 
    	Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
    	Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
	*/
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ItemResponse>> query(@RequestBody QueryRequest queryRequest) {
		try {
			List<ItemResponse> response = service.doQuery(
					queryRequest.getAppDate(), 
					queryRequest.getProductId(), 
					queryRequest.getBrandId());
			return ResponseEntity.ok(response);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}

	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> version() {
		try {
			return ResponseEntity.ok("1.0.0");
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}

	}
	
}
