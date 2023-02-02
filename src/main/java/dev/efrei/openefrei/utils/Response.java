package dev.efrei.openefrei.utils;

import com.google.gson.Gson;

public class Response {
	
	private int code;
	private String message;
	
	private Response(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String get(int code, String message) {
		return new Gson().toJson(new Response(code, message));
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
