package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bank.Response.ResponseStructure;

@ControllerAdvice
public class Exceptioncontroller {
	@ExceptionHandler(value = myexception.class)
	public ResponseEntity<ResponseStructure<String>> idNotFound(myexception ie) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		responseStructure.setCode(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessgae("Request failed");
		responseStructure.setData(ie.toString());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);


	}


}
