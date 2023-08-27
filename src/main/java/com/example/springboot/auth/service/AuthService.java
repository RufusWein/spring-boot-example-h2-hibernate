package com.example.springboot.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot.auth.dao.UserRepository;
import com.example.springboot.auth.model.AuthResponse;
import com.example.springboot.auth.model.LoginRequest;
import com.example.springboot.auth.model.RegisterRequest;
import com.example.springboot.auth.model.Role;
import com.example.springboot.auth.model.User;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTService jwtService;
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token=jwtService.getToken(user);
		return new AuthResponse(token);
	}

	public AuthResponse register(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setFirtsname(request.getFirtsname());
		user.setLastname(request.getLastname());
		user.setCountry(request.getCountry());
		user.setRole(Role.USER);
		
		userRepository.save(user);
		
		return new AuthResponse(jwtService.getToken(user));
	}
		
	public Optional<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}

}
