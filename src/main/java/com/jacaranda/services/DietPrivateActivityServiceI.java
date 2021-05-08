package com.jacaranda.services;

import com.jacaranda.model.DietPrivateActivity;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.dto.DietProgressBarDto;

public interface DietPrivateActivityServiceI {

	DietPrivateActivity createPrivateActivity(String username, DietPrivateActivity privateActivity);
	
	DietRegister createRegister(String username, DietRegister register);
	
	DietPrivateActivity getPrivateActivity(String username);
	
	DietProgressBarDto getProgressBar(String username);
}
