package com.example.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springboot.auth.service.AuthService;

@Configuration
@Import(CustomRequestFilter.class)
@EnableWebSecurity
public class AuthorizationServerConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private AuthService authService;
    
    @Autowired
    private CustomRequestFilter customRequestFilter;    
    
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    LOGGER.info("AuthorizationServerConfig.passwordEncoder - instance password encoder");

		return new BCryptPasswordEncoder();
	}

    @Bean
    public FilterRegistrationBean<CustomRequestFilter> customRequestFilter(){
	    LOGGER.info("AuthorizationServerConfig.customRequestFilter - create bean filter per url patterns");
	    
        FilterRegistrationBean<CustomRequestFilter> beanFilter = new FilterRegistrationBean<>();
        beanFilter.setFilter(new CustomRequestFilter());
        beanFilter.addUrlPatterns("/h2");
        beanFilter.addUrlPatterns("/h2/**");
        beanFilter.addUrlPatterns("/api/**");        
        beanFilter.addUrlPatterns("/api");
        return beanFilter;
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
	    LOGGER.info("AuthorizationServerConfig.webSecurityCustomizer - ignore /h2 url");

    	return (web) -> web.ignoring().antMatchers("/h2/**");
    }
    
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    LOGGER.info("AuthorizationServerConfig.authenticationProvider - get authentication provider");

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		authenticationProvider.setUserDetailsService(userDetailService());
		authenticationProvider.setPasswordEncoder(passwordEncoder);		
		return authenticationProvider;
	}
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {		
	    LOGGER.info("AuthorizationServerConfig.filterChain - set httpSecurity config");

	    return httpSecurity
				.csrf().disable()		
				.authorizeRequests()
					.antMatchers("/api/v1/query").authenticated()
					.antMatchers("/api/v1/version").permitAll()
					.antMatchers("/auth/**").permitAll()				
					.antMatchers("/h2").permitAll()
					.anyRequest().authenticated()
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.addFilterBefore(customRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.build();	
	}
	
    @Bean
    public UserDetailsService userDetailService() {
        return username -> authService.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not fournd"));
    }
    
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    LOGGER.info("AuthorizationServerConfig.authenticationManager - get authentication manager");

		return config.getAuthenticationManager();
	}

}
