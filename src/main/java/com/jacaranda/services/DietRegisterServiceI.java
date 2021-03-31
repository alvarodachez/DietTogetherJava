package com.jacaranda.services;

import java.util.List;

import com.jacaranda.exceptions.DietRegisterException;
import com.jacaranda.model.DietDish;
import com.jacaranda.model.DietRegister;

public interface DietRegisterServiceI {

	public DietRegister createRegister(String username, DietRegister register) throws DietRegisterException;
	
	public List<DietRegister> getRegistersByUsername(String username);
	
	public DietRegister verifyRegister(String username, Long id);
	
	public DietRegister declineRegister(String username, Long id);
	
	public List<DietRegister> getRegistersToVerify(String username);
	
	public List<DietDish> getAthleteDishesByName(String initials, String username);
}
