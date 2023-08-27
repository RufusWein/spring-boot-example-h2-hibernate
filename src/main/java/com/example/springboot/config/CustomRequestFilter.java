package com.example.springboot.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springboot.auth.service.JWTService;

@Component
public class CustomRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailService;
    
    @Autowired
    private JWTService jwtService;
        
    /*
     * doFilterInternal always is called when it had been register access (from filterChain bean)
     */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String token = getTokenFromRequest(request);
	    final String path = request.getRequestURI();
	    
	    LOGGER.info("CustomRequestFilter.doFilterInternal - path = {}, token = {} ",path, token);
	    
	    if (path.contains("/h2/") || token == null) {
	    	filterChain.doFilter(request, response);
	    	return;
	    }

	    final String username = jwtService.getUsernameFromToken(token);
	    
	    if (username!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
	    	UserDetails userDetails = userDetailService.loadUserByUsername(username);
	 
	    	if (jwtService.isTokenValid(token, userDetails)) {
	    		UsernamePasswordAuthenticationToken authToken = 
	    				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    		SecurityContextHolder.getContext().setAuthentication(authToken);
	    	}
	    }
	    
	    filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (StringUtils.hasText(authHeader) && authHeader.contains("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

}
