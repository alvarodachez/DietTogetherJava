package com.jacaranda.restcontroller;

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

import com.jacaranda.model.DietPrivateActivity;
import com.jacaranda.model.DietRegister;
import com.jacaranda.services.DietPrivateActivityServiceI;

@RestController
@RequestMapping("/private-activity")
@CrossOrigin(origins = "*")
public class DietPrivateActivityController {
	
	@Autowired
	DietPrivateActivityServiceI privateActivityService;

	@PostMapping("/create-private-activity/{username}")
	public ResponseEntity<?> createPrivateActivity(@PathVariable String username, @RequestBody DietPrivateActivity privateActivity){
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(privateActivityService.createPrivateActivity(username, privateActivity));
		
	}
	
	@PostMapping("/create-register/{username}")
	public ResponseEntity<?> createRegister(@PathVariable String username, @RequestBody DietRegister register){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(privateActivityService.createRegister(username, register));
	}
	
	@GetMapping("/get-private-activity/{username}")
	public ResponseEntity<?> getPrivateActivity(@PathVariable String username){
		
		return ResponseEntity.status(HttpStatus.OK).body(privateActivityService.getPrivateActivity(username));
	}
	
	@GetMapping("/get-progress-bar/{username}")
	public ResponseEntity<?> getProgressBar(@PathVariable String username){
		
		return ResponseEntity.status(HttpStatus.OK).body(privateActivityService.getProgressBar(username));
	}
}
