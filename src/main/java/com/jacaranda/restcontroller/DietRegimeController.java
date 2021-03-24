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

import com.jacaranda.model.DietDish;
import com.jacaranda.services.impl.DietRegimeServiceImpl;

@RestController
@RequestMapping("/regime")
@CrossOrigin(origins = "*")
public class DietRegimeController {

	@Autowired
	private DietRegimeServiceImpl regimeService;

	@PostMapping("/create-dish/{username}")
	public ResponseEntity<?> createDish(@PathVariable String username, @RequestBody DietDish dish) {

		return ResponseEntity.status(HttpStatus.CREATED).body(regimeService.createDish(username, dish));
	}

	@GetMapping("/get-dishes/{username}")
	public ResponseEntity<?> getDishesByUsername(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(regimeService.getDishesByUsername(username));
	}
	
	@PostMapping("/create-regime-structure/{username}")
	public ResponseEntity<?> createRegimeStructure(@PathVariable String username){
		return ResponseEntity.status(HttpStatus.CREATED).body(regimeService.createRegimeStructure(username));
	}
	
	@PostMapping("/add-dish-day/{username}&&{meal}&&{dish}")
	public ResponseEntity<?> addDishToDay(@PathVariable("username") String username,@PathVariable("meal") String meal, @PathVariable("dish") String dish){
		return ResponseEntity.status(HttpStatus.CREATED).body(regimeService.addDishToDay(username,Long.valueOf(meal),Long.valueOf(dish)));
	}

}
