package com.jacaranda.security.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.model.dto.DietUserDTO;
import com.jacaranda.security.services.impl.DietUserServiceImpl;

@RestController
@RequestMapping("/user")
public class DietUserController {

	@Autowired
	private DietUserServiceImpl userService;

	@PostMapping("/")
	private ResponseEntity<DietUser> createNewUser(@RequestBody DietUserDTO user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(user));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
