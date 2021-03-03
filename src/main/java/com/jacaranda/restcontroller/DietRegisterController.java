package com.jacaranda.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietRegister;
import com.jacaranda.services.DietRegisterServiceI;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class DietRegisterController {
	
	
	@Autowired
	private DietRegisterServiceI registerService;
	
	@PostMapping("/create-register/{username}")
	public ResponseEntity<DietRegister> createRegister(@PathVariable("username") String username, @RequestBody DietRegister register) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(registerService.createRegister(username, register));
 
	}

}
