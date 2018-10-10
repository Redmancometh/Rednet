package com.bcurry.rednet.adapter;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bcurry.rednet.pojo.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String AUTH_SYS_ERROR = "Something went wrong while parsing /login request body";

	public SimpleAuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(req, res, chain);
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("ATTEMPTING AUTHENTICATION!");
		try {
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			AuthRequest authRequest = objectMapper.readValue(reader, AuthRequest.class);
			request.setAttribute("username", authRequest.getUsername());
			request.setAttribute("password", authRequest.getPassword());
			System.out.println(authRequest);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(), authRequest.getPassword());
			setDetails(request, token);
			System.out.println(getAuthenticationManager() == null);
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			response.setHeader("Access-Control-Expose-Headers", "Location");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			Authentication auth = getAuthenticationManager().authenticate(token);
			System.out.println("Is authenticated: " + auth.isAuthenticated());
			System.out.println("Is authenticated: " + auth.getName());
			System.out.println("Is authenticated: " + auth.getAuthorities().size());
			return auth;
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalAuthenticationServiceException(AUTH_SYS_ERROR, e);
		}
	}
}