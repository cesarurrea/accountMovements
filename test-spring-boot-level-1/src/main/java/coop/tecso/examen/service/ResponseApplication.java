package coop.tecso.examen.service;

import java.io.Serializable;
import java.util.List;

import coop.tecso.examen.common.enums.ResponseType;

public class ResponseApplication<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T response;
	private String message;
	private ResponseType responseType;
	private List<T> list;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getMessage() {
		return message;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
