package com.jacaranda.exceptions;

import com.jacaranda.common.DietExceptionCode;

@SuppressWarnings("serial")
public class DietUserException extends Exception{

private final DietExceptionCode code;
	
	public DietUserException(DietExceptionCode code) {
		super();
		this.code = code;
	}
	
	public DietExceptionCode getCode() {
		return this.code;
	}
}
