package com.jacaranda.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DietExceptionCode implements Serializable{

	/**
	 * ERRORES CON GRUPOS
	 */
	ALREDY_IN_GROUP("Ya estas en un grupo"),
	/**
	 * ERRORES CON REGISTROS
	 */
	ONE_REGISTER_PER_WEEK("Un registro cada semana"),
	/**
	 * ERRORES CON USUARIOS
	 */
	ALREDY_USER_EXISTS("El usuario ya existe"),
	/**
	 * ERRORES CON SOLICITUDES
	 */
	SELF_FRIEND_REQUEST("No puedes ser amigo de ti mismo"),
	ALREDY_REQUEST("Ya has mandado una solicitud a este usuario"),
	ALREDY_FRIEND("Este usuario ya es tu amigo"),

	/**
	 * ERRORES DESCONOCIDOS
	 */
	UNKNOWN_ERROR("Error desconocido, contacte con administrador.");

	private final String message;

	private DietExceptionCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
