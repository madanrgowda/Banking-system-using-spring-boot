package com.bank.Response;

import java.util.List;

import com.bank.Entity.bankAccount;

import lombok.Data;

@Data
public class ResponseStructure<T> {

	int code;
	String messgae;
	T data;
}
