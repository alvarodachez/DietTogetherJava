package com.jacaranda.security.model.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacaranda.security.model.DietRole;
import com.jacaranda.security.model.DietUser;

@Service
public class DietUserDTOConverter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	/** PASA DE DTO A USUARIO */
	public DietUser fromUserDTOToUser(DietUserDTO dto) {
		DietUser user = new DietUser();
		user.setUsername(dto.getUsername());
		// Recall
		// passwordEncoder.matches("1234",
		// "$2a$10$klljZRCKUsItpuhhWjt/OOuQotVva/ADfOQDBtWNIJBBX5d88sZNm");
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(DietRole.USER));
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		user.setLastPasswordChange(LocalDateTime.now());
		user.setLocked(false);
		user.setEnabled(true);
		user.setAuthenticationAttempts(0);
		user.setPasswordPolicyExpDate(LocalDateTime.now().plusDays(180));
		return user;

	}

	/** PASA DE USUARIO A DTO */
	public DietUserDTO fromUserToUserDTO(DietUser user) {
		DietUserDTO dto = new DietUserDTO();
		dto.setUsername(user.getUsername());
		dto.setRoles(user.getRoles());
		return dto;
	}
}
