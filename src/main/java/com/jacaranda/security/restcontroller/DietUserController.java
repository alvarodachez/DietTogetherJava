package com.jacaranda.security.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jacaranda.security.model.dto.DietUserDTO;
import com.jacaranda.security.services.impl.DietUserServiceImpl;

@RestController
@RequestMapping("/user")
public class DietUserController {

	@Autowired
	private DietUserServiceImpl userService;

	@PostMapping("/sign-up")
	public ResponseEntity<DietUserDTO> signUp(@RequestBody DietUserDTO userDTO) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userDTO));
		}catch(Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<DietUserDTO> login(@RequestBody DietUserDTO userDTO){
		// Created only to retrieve the Bearer token once authenticated
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}

}
