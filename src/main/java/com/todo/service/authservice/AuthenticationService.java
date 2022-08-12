package com.todo.service.authservice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

import com.todo.Interface.AuthServiceInterface;
import com.todo.model.User;
import com.todo.repositories.UserRepository;

@Service
public class AuthenticationService implements AuthServiceInterface {
	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseEntity validateBasicAuthentication(String basicAuthHeaderValue) {
		try {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			if (StringUtils.hasText(basicAuthHeaderValue) && basicAuthHeaderValue.toLowerCase().startsWith("basic")) {
				String base64Credentials = basicAuthHeaderValue.substring("Basic".length()).trim();
				byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				String credentials = new String(credDecoded, StandardCharsets.UTF_8);
				final String[] values = credentials.split(":", 2);

				if (values.length == 2) {
					String username = values[0];
					String password = values[1];
					User user = userRepository.findByEmailAddress(username);
					if (user != null && bCryptPasswordEncoder.matches(password, user.getUserPassword())) {
						if (user.getEmailValidated()) {
							return new ResponseEntity(HttpStatus.OK);

						} else {
							return new ResponseEntity(HttpStatus.FORBIDDEN);
						}
					} else {
						return new ResponseEntity(HttpStatus.UNAUTHORIZED);
					}
				}
			}
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public String getUserName(HttpServletRequest request) {
		try {
			Map<String, Object> returnValue = new HashMap<>();
			Enumeration<String> hearderNames = request.getHeaderNames();
			while (hearderNames.hasMoreElements()) {
				String headerName = hearderNames.nextElement();
				returnValue.put(headerName, request.getHeader(headerName));
			}
			final String AUTH_HEADER_PARAMETER_AUTHORIZATION = "authorization";
			String basicAuthHeaderValue = request.getHeader(AUTH_HEADER_PARAMETER_AUTHORIZATION);
			String base64Credentials = basicAuthHeaderValue.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			final String[] values = credentials.split(":", 2);
			String username = values[0];
			return username != "null" ? username : null;
		} catch (Exception e) {
			return null;
		}

	}

}
