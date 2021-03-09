package com.jacaranda.services;

import java.util.List;

import com.jacaranda.exceptions.DietRegisterException;
import com.jacaranda.model.DietRegister;

public interface DietRegisterServiceI {

	public DietRegister createRegister(String username, DietRegister register) throws DietRegisterException;
	
	public List<DietRegister> getRegistersByUsername(String username);
}
