package com.jacaranda.exceptions;

import com.jacaranda.common.DietExceptionCode;

@SuppressWarnings("serial")
public class DietRequestException extends Exception{
	
	private final DietExceptionCode code;
	
	public DietRequestException(DietExceptionCode code) {
		super();
		this.code = code;
	}
	
	public DietExceptionCode getCode() {
		return this.code;
	}

}
