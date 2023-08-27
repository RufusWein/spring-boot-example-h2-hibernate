package com.example.springboot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springboot.prices.model.QueryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PricesControllerTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PricesControllerTest.class);
    
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	SimpleDateFormat sdf;
	
	@BeforeAll
	public void init(){
		new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Test
	@DisplayName("Test 1: petición a las 19:00 del día 14 del producto 35455 para la brand 1 (CADENA1)")
	public void queryTest1() throws Exception {
		
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setAppDate(sdf.parse("2022-08-02"));
		queryRequest.setProductId(35455);
		queryRequest.setBrandId(1);
		
	    String response = mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(queryRequest)))
	        .andExpect(status().is(200))            
	        .andReturn()
            .getResponse()
            .getContentAsString();
	    
	    LOGGER.info("Test 1: Respuesta: {}",response);
	}
	
	@Test
	@DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (CADENA1)")
	public void queryTest2() throws Exception {
		
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setAppDate(sdf.parse("2022-08-02"));
		queryRequest.setProductId(35455);
		queryRequest.setBrandId(1);
		
		String response = mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(queryRequest)))
	        .andExpect(status().is(200))
	        .andReturn()
            .getResponse()
            .getContentAsString();

	    LOGGER.info("Test 2: Respuesta: {}",response);
	}
	
	@Test
	@DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (CADENA1)")
	public void queryTest3() throws Exception {
		
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setAppDate(sdf.parse("2022-08-02"));
		queryRequest.setProductId(35455);
		queryRequest.setBrandId(1);
		
		String response = mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(queryRequest)))
	        .andExpect(status().is(200))
	        .andReturn()
            .getResponse()
            .getContentAsString();
		
	    LOGGER.info("Test 3: Respuesta: {}",response);
	}

	@Test
	@DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (CADENA1)")
	public void queryTest4() throws Exception {
		
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setAppDate(sdf.parse("2022-08-02"));
		queryRequest.setProductId(35455);
		queryRequest.setBrandId(1);
		
		String response = mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(queryRequest)))
	        .andExpect(status().is(200))
	        .andReturn()
            .getResponse()
            .getContentAsString();
		
	    LOGGER.info("Test 4: Respuesta: {}",response);
	}
	
	@Test
	@DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (CADENA1)")
	public void queryTest5() throws Exception {
		
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setAppDate(sdf.parse("2022-08-02"));
		queryRequest.setProductId(35455);
		queryRequest.setBrandId(1);
		
		String response = mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(queryRequest)))
	        .andExpect(status().is(200))
	        .andReturn()
            .getResponse()
            .getContentAsString();
		
	    LOGGER.info("Test 5: Respuesta: {}",response);
	}

}
