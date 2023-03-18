package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.Entity.Customer;
import com.bank.Entity.Login;
import com.bank.Response.ResponseStructure;
import com.bank.Service.ManagementService;
import com.bank.exception.myexception;

@SpringBootApplication
public class BankApplication {

	ManagementService mg;
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	
}
