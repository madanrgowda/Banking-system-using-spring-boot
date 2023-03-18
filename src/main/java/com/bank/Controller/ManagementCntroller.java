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

import com.bank.Entity.Customer;
import com.bank.Entity.Login;
import com.bank.Entity.Management;
import com.bank.Entity.bankAccount;
import com.bank.Response.ResponseStructure;
import com.bank.Service.ManagementService;
import com.bank.exception.myexception;

@RestController
@RequestMapping("management")
public class ManagementCntroller {
	
	@Autowired
	ManagementService mngser;

	@PostMapping("add")
	public ResponseStructure<Management> addDeatils(@RequestBody Management management)
	{
		return mngser.addDetails(management);
	}
	@PostMapping("login")
	public ResponseStructure<Management> login(@RequestBody Management management) throws myexception{
		return mngser.login(management);

	}
	
	@GetMapping("accounts")
	public ResponseStructure<List<bankAccount>> fetchAllAccounts() throws myexception{
		return mngser.fetchAllAccounts();
	}
	
	@PutMapping("accountchange/{acno}")
	public ResponseStructure<bankAccount> changeStatus(@PathVariable long acno)
	{
		return mngser.changeStatus(acno);
	}
}
