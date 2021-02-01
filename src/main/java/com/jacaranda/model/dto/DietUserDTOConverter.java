package com.jacaranda.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jacaranda.model.DietUser;
import com.jacaranda.model.DietUserRole;


@Component
public class DietUserDTOConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DietUser fromUserDTOToUser(DietUserDTO dto) {
		DietUser user  = new DietUser();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(DietUserRole.USER));
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		user.setLastPasswordChange(LocalDateTime.now());
		return user;
		
	}
	
	public DietUserDTO fromUserToUserDTO(DietUser user) {
		DietUserDTO dto = new DietUserDTO();
		dto.setUsername(user.getUsername());
		dto.setRoles(user.getRoles());
		return dto;
	}
}
