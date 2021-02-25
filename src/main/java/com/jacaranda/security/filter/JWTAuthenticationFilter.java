package com.jacaranda.security.filter;

import static com.jacaranda.security.common.SecurityConstants.HEADER_STRING;
import static com.jacaranda.security.common.SecurityConstants.TOKEN_PREFIX;
import static com.jacaranda.security.filter.jwt.JWTTokenProvider.generateToken;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jacaranda.security.common.SecurityConstants;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.model.dto.DietUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebFilter
@CrossOrigin(origins = "*")
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstants.LOG_IN);
    }
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		DietUserDTO user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), DietUserDTO.class);
			

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return 	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), 
																						   user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "*");
		response.addHeader("Content-Type", "application/json");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateToken(((DietUser)authResult.getPrincipal())));
        response.getWriter().write(generateToken(((DietUser)authResult.getPrincipal())));
		
	}
	
	
	

}
