package com.jacaranda.exceptions;

import com.jacaranda.common.DietExceptionCode;

@SuppressWarnings("serial")
public class DietRegisterException extends Exception {

	private final DietExceptionCode code;

	public DietRegisterException(DietExceptionCode code) {
		super();
		this.code = code;
	}

	public DietExceptionCode getCode() {
		return this.code;
	}
}
