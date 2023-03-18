package com.bank.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.Entity.bankAccount;
import com.bank.Entity.bankTranscation;
import com.bank.Entity.Customer;
import com.bank.Entity.Login;
import com.bank.Response.ResponseStructure;
import com.bank.Service.CustomeService;
import com.bank.exception.myexception;


@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	CustomeService custser;
	
	@PostMapping("add")
	public ResponseStructure<Customer> save(@RequestBody Customer customer)
	{
		return custser.save(customer);
	}
	@PutMapping("otp/{id}/{otp}")
	public ResponseStructure<Customer> otpverification(@PathVariable int id,@PathVariable int otp) throws myexception{
		return custser.verfiy(id,otp);
	}
	
	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws myexception{
		return custser.login(login);
	}
	@PostMapping("account/{id}/{type}")
	public ResponseStructure<Customer> createAccount(@PathVariable int id, @PathVariable String type) throws myexception{
		
		
		return custser.createAccount(id,type) ;
		
	}
	@GetMapping("accounts/{id}")
	public ResponseStructure<List<bankAccount>> fetchAllTrue(@PathVariable int id) throws myexception
	{
		return custser.fetchAllTrue(id);
	}

	@GetMapping("account/check/{acno}")
	public ResponseStructure<Double> checkBalance(@PathVariable long acno)
	{
		return custser.checkBalance(acno);
	}
	 
	@PutMapping("account/deposit/{acno}/{amount}")
	public ResponseStructure<bankAccount> deposit(@PathVariable long acno, @PathVariable double amount){
		return custser.deposit(acno,amount);
		
	}
	
	@PutMapping("account/withdraw/{acno}/{amount}")
	public ResponseStructure<bankAccount> withdraw(@PathVariable long acno, @PathVariable double amount) throws myexception{
		return custser.withdraw(acno,amount);
}
 @GetMapping("account/viewtranscation/{acno}")
 public ResponseStructure<List<bankTranscation>> viewtranscation(@PathVariable long acno) throws myexception {
	 return custser.viewtranscation(acno);
 }
}