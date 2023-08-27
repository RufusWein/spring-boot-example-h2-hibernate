package com.example.springboot.auth.service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private static final String SECRET_KEY="85F366EC918808496730249FEDE1E62FA96EBC17A3222891";

	public String getToken(UserDetails user) {
		Map<String, String> extraClaims = new HashMap<>();
		
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+60000*3))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        final boolean isTokenExpired = getClaim(token, Claims::getExpiration).before(new Date());
        return (username.equals(userDetails.getUsername())&& !isTokenExpired);
	}
	
	public static void main(String [] args) {
		System.out.println(toHex(getSecureRandomKey("AES",192).getEncoded()));
	}

	private static Key getSecureRandomKey(String cipher, int keySize) {
	    byte[] secureRandomKeyBytes = new byte[keySize / 8];
	    SecureRandom secureRandom = new SecureRandom();
	    secureRandom.nextBytes(secureRandomKeyBytes);
	    return new SecretKeySpec(secureRandomKeyBytes, cipher);
	}
	
	private static String toHex(final byte[] data) {
	    final StringBuilder sb = new StringBuilder(data.length * 2);
	    for (final byte b : data) {
	        sb.append(String.format("%02X", b));
	    }
	    return sb.toString();
	}
	
    private Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
     }

}
