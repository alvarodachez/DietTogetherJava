package com.jacaranda.services;

import java.util.List;

import com.jacaranda.model.DietRegister;

public interface DietRegisterServiceI {

	public DietRegister createRegister(String username, DietRegister register);
	
	public List<DietRegister> getRegistersByUsername(String username);
}
