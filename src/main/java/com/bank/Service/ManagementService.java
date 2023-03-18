package com.bank.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.Entity.Customer;
import com.bank.Entity.Login;
import com.bank.Entity.Management;
import com.bank.Entity.bankAccount;
import com.bank.Response.ResponseStructure;
import com.bank.Rposiory.Bankrepository;
import com.bank.Rposiory.ManaementRepository;
import com.bank.exception.myexception;

@Service
public class ManagementService {
	@Autowired
	ManaementRepository mngrepo;
	
	@Autowired
	Bankrepository bankrep;

	public ResponseStructure<Management> addDetails(Management management) {

        ResponseStructure<Management> structure=new ResponseStructure<Management>();
        
        structure.setCode(HttpStatus.CREATED.value());
        structure.setMessgae("Account Created Successfully");
        structure.setData(mngrepo.save(management));
		return structure;
	}

	public ResponseStructure<Management> login(Management management) throws myexception {
	
		ResponseStructure<Management> structure=new ResponseStructure<>();
		Management manage=mngrepo.findByEmail(management.getEmail());
		if(manage==null) {
			throw new myexception("invalid customer id");
		}else {
		if(manage.getPassword().equals(manage.getPassword()))	{
			
				
				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMessgae("login sucessfully");
				structure.setData(manage);
				
			
			
		}else {
			throw new myexception("invalid password");
		}
		}
		return structure;
		}

	public ResponseStructure<List<bankAccount>> fetchAllAccounts() throws myexception {
		ResponseStructure<List<bankAccount>> structure1=new ResponseStructure<>();
		List<bankAccount> list=bankrep.findAll();
	if(list.isEmpty()) {
		throw new myexception("no account found");
	}else {
		structure1.setCode(HttpStatus.FOUND.value());
		structure1.setMessgae("data found");
		structure1.setData(list);
	}
	return structure1;
	}

	public ResponseStructure<bankAccount> changeStatus(long acno) {
		ResponseStructure<bankAccount> structure=new ResponseStructure<bankAccount>();

		Optional<bankAccount> optional=bankrep.findById(acno);
		bankAccount account=optional.get();
		if(account.isStatus())
		{
			account.setStatus(false);
		}
		else{
		account.setStatus(true);
		}
		structure.setCode(HttpStatus.OK.value());
		structure.setMessgae("Changed Status Success");
		structure.setData(bankrep.save(account));
		return structure;
	}
	}
		
	

