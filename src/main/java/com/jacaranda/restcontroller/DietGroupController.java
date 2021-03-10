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

import com.jacaranda.exceptions.DietGroupException;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietGroupRequest;
import com.jacaranda.model.dto.DietProgressBarDto;
import com.jacaranda.services.DietGroupServiceI;

@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "*")
public class DietGroupController {

	@Autowired
	DietGroupServiceI groupService;

	@PostMapping("/create-group/{username}")
	public ResponseEntity<?> createGroup(@PathVariable String username, @RequestBody DietGroup group) {
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(username, group));
		}catch(DietGroupException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getCode());
		}

		
	}

	@PostMapping("/send-group-request/{claimant}&&{requested}")
	public ResponseEntity<DietGroupRequest> sendGroupRequest(@PathVariable("claimant") String claimantUsername,
			@PathVariable("requested") String requestedUsername) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(groupService.sendGroupRequest(claimantUsername, requestedUsername));
	}

	@PostMapping("/accept-group-request/{id}")
	public ResponseEntity<?> acceptGroupRequest(@PathVariable("id") String id) {

		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(groupService.acceptGroupRequest(Long.valueOf(id)));
		}catch(DietGroupException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getCode());
		}
	}

	@PostMapping("/reject-group-request/{id}")
	public ResponseEntity<DietGroupRequest> rejectGroupRequest(@PathVariable("id") String id) {

		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.rejectGroupRequest(Long.valueOf(id)));
	}

	@GetMapping("/get-group-request/{username}")
	public ResponseEntity<List<DietGroupRequest>> getGroupRequests(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(groupService.getGroupRequests(username));
	}

	@GetMapping("/get-progress-bar/{username}")
	public ResponseEntity<DietProgressBarDto> getProgressBar(@PathVariable("username") String username) {
		return ResponseEntity.status(HttpStatus.OK).body(groupService.getProgressBar(username));
	}
	
	@PostMapping("/get-out-group/{username}")
	public ResponseEntity<?>getOutGroup(@PathVariable("username") String username){
		return ResponseEntity.status(HttpStatus.OK).body(groupService.getOutGroup(username));
	}
}
