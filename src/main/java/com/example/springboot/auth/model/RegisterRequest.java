package com.example.springboot.auth.model;

import lombok.Data;

@Data
public class RegisterRequest {

	private String username;
	
	private String password;
	
	private String firtsname;
	
	private String lastname;
	
	private String country;

}
