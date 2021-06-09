package com.jacaranda.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.services.DietChartServiceI;

@RestController
@RequestMapping("/chart")
@CrossOrigin(origins = "*")
public class DietChartController {

	@Autowired
	DietChartServiceI chartService;

	@GetMapping("/get-profile-total-register/{username}")
	public ResponseEntity<?> getTotalProfileRegisters(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(chartService.totalProfileRegisters(username));
	}

	@GetMapping("/get-profile-total-weight-difference-register/{username}")
	public ResponseEntity<?> getTotalProfileWeightDifferenceRegisters(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(chartService.totalProfileWeightDifferenceRegisters(username));
	}
	
	@GetMapping("/get-group-total-register/{username}")
	public ResponseEntity<?> getTotalGroupRegisters(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(chartService.totalGroupRegisters(username));
	}
	
	@GetMapping("/get-group-points/{username}")
	public ResponseEntity<?> getGroupPoints(@PathVariable("username") String username){
		return ResponseEntity.status(HttpStatus.OK).body(chartService.pointsGroup(username));
	}

}
