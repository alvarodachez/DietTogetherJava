package com.jacaranda.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.exceptions.DietRegisterException;
import com.jacaranda.model.DietRegister;
import com.jacaranda.services.DietRegisterServiceI;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class DietRegisterController {

	@Autowired
	private DietRegisterServiceI registerService;

	@PostMapping("/create-register/{username}")
	public ResponseEntity<?> createRegister(@PathVariable("username") String username,
			@RequestBody DietRegister register) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(registerService.createRegister(username, register));
		}catch(DietRegisterException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getCode());
		}

	}

	@GetMapping("/get-registers/{username}")
	public ResponseEntity<List<DietRegister>> getRegistersByUsername(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(registerService.getRegistersByUsername(username));
	}

}
