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

import com.jacaranda.model.DietFriendRequest;
import com.jacaranda.model.DietGroup;
import com.jacaranda.model.DietGroupRequest;
import com.jacaranda.services.DietGroupServiceI;

@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "*")
public class DietGroupController {

	@Autowired
	DietGroupServiceI groupService;

	@PostMapping("/create-group/{username}")
	public ResponseEntity<DietGroup> createGroup(@PathVariable String username, @RequestBody DietGroup group) {

		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(username, group));
	}
	
	@PostMapping("/send-group-request/{claimant}&&{requested}")
	public ResponseEntity<DietGroupRequest> sendGroupRequest(@PathVariable("claimant") String claimantUsername, @PathVariable("requested") String requestedUsername){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.sendGroupRequest(claimantUsername, requestedUsername));
	}
	
	@PostMapping("/accept-group-request/{id}")
	public ResponseEntity<DietGroupRequest> acceptGroupRequest(@PathVariable("id") String id){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.acceptGroupRequest(Long.valueOf(id)));
	}
	
	@PostMapping("/reject-group-request/{id}")
	public ResponseEntity<DietGroupRequest> rejectGroupRequest(@PathVariable("id") String id){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(groupService.rejectGroupRequest(Long.valueOf(id)));
	}
}
