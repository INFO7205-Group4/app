package com.todo.service.authservice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todo.Interface.AuthServiceInterface;
import com.todo.model.User;
import com.todo.repositories.UserRepository;

@Service
public class AuthenticationService implements AuthServiceInterface {
	@Autowired
	UserRepository userRepository;

	@Override
	public Boolean validateBasicAuthentication(String basicAuthHeaderValue) {
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
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

}
