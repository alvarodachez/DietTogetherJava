package com.jacaranda.exceptions;

import com.jacaranda.common.DietExceptionCode;

@SuppressWarnings("serial")
public class DietGroupException extends Exception {

	private final DietExceptionCode code;

	public DietGroupException(DietExceptionCode code) {
		super();
		this.code = code;
	}

	public DietExceptionCode getCode() {
		return this.code;
	}
}
