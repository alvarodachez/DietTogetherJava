package com.jacaranda.security.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.model.DietRole;

@Service
public class DietUserDTOConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public DietUser fromUserDTOToUser(DietUserDTO dto) {
		DietUser user = new DietUser();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(DietRole.USER));
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
