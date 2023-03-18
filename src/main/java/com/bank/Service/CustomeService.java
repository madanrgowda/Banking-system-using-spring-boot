package com.bank.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.Entity.bankAccount;
import com.bank.Entity.bankTranscation;
import com.bank.Entity.Customer;
import com.bank.Entity.Login;
import com.bank.Response.MailVerifaction;
import com.bank.Response.ResponseStructure;
import com.bank.Rposiory.Bankrepository;
import com.bank.Rposiory.CustomerRepository;
import com.bank.exception.myexception;

@Service
public class CustomeService {
	@Autowired
	CustomerRepository custrepo;
	
	@Autowired
	MailVerifaction mailVerifaction;
	
	@Autowired
	bankAccount accounts;
	
	@Autowired
	Bankrepository bankrep;
	
	@Autowired
	bankTranscation banktrans;
	
	
	public ResponseStructure<Customer> save(Customer customer)
	{
		ResponseStructure<Customer>  structure=new ResponseStructure<>();
		
		int age=Period.between(customer.getDob().toLocalDate(),LocalDate.now()).getYears();
		customer.setAge(age);
		if(age<18)
		{
			structure.setCode(HttpStatus.CONFLICT.value());
			structure.setMessgae("Your Age Should be 18+ to create Account ");
			structure.setData(customer);
		}
		else
		{
			Random random=new Random();
			int otp=random.nextInt(100000,999999);
			customer.setOtp(otp);
			
			mailVerifaction.sendMail(customer);
			
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setMessgae("Varification Mail Sent ");
			structure.setData(custrepo.save(customer));
		}
		
		return structure;
	}

	public ResponseStructure<Customer> verfiy(int id, int otp) throws myexception {
		ResponseStructure<Customer> structure=new ResponseStructure<>();
		Optional<Customer> optional=custrepo.findById(id);
		if(optional.isEmpty()) {
			throw new myexception("check id and try again");
		}else {
			Customer customer=optional.get();
			if(customer.getOtp()==otp) {
				structure.setCode(HttpStatus.CREATED.value());
				
				structure.setMessgae("Account created successduly");
				
				customer.setStatus(true);
				structure.setData(custrepo.save(customer));
			}else {
				throw new myexception("otp invalid");
			}
		}
		
		return structure;
	}

	public ResponseStructure<Customer> login(Login login) throws myexception {
	 ResponseStructure<Customer> structure=new ResponseStructure<>();
	
	Optional<Customer> optional=custrepo.findById(login.getId());
	if(optional.isEmpty()) {
		throw new myexception("invalid customer id");
	}else {
		 Customer customer=optional.get();
	if(customer.getPassword().equals(login.getPassword()))	{
		if(customer.isStatus()) {
			
			structure.setCode(HttpStatus.ACCEPTED.value());
			structure.setMessgae("login sucessfully");
			structure.setData(customer);
			
		}else {
			throw new myexception("verify the your email");
		}
		
	}else {
		throw new myexception("invalid password");
	}
	}
	return structure;
	}

	public ResponseStructure<Customer> createAccount(int id, String type) throws myexception {
	   ResponseStructure<Customer> structure=new ResponseStructure<>();
	   Optional<Customer> optional=custrepo.findById(id);
		if(optional.isEmpty()) {
			throw new myexception("check id and try again");
		}else {
			Customer customer=optional.get();
			 List<bankAccount> list=customer.getAccounts();
			 boolean flag=true;
			    for(bankAccount account:list) {
			    	if(account.getType().equals(type)) {
			    		flag=false;
			    		break;
			    	}
			    }
			    	if(!flag) {
			    		throw new myexception("Account already exits");
			    	}else{
		        accounts.setType(type);
		        if(type.equals("savings")) {
		    	accounts.setAmountlimit(50000);
		       }else {
		    	accounts.setAmountlimit(10000);
		    }
		        
		        list.add(accounts);
		    	customer.setAccounts(list);
		}
			   structure.setCode(HttpStatus.ACCEPTED.value());
			   structure.setMessgae("Account created wait management approve");
			    	structure.setData(custrepo.save(customer));
			    }
		 return structure;
	}

	public ResponseStructure<List<bankAccount>> fetchAllTrue(int id) throws myexception {
		ResponseStructure<List<bankAccount>> structure=new ResponseStructure<List<bankAccount>>();

		Optional<Customer> optional=custrepo.findById(id);
		Customer customer=optional.get();
		List<bankAccount> list=customer.getAccounts();

		List<bankAccount> res=new ArrayList<bankAccount>();
		for(bankAccount account:list)
		{
			if(account.isStatus())
			{
				res.add(account);
			}
		}

		if(res.isEmpty())
		{
			throw new myexception("No Active Accounts Found");
		}
		else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessgae("Accounts FOund");
			structure.setData(res);   
		}
		return structure;
	}
	

	public ResponseStructure<Double> checkBalance(long acno) {
		
			ResponseStructure<Double> structure=new ResponseStructure<Double>();

			Optional<bankAccount> optional=bankrep.findById(acno);
			bankAccount account=optional.get();

			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessgae("Data Found");
			structure.setData(account.getAmount());
			return structure;
		}

	public ResponseStructure<bankAccount> deposit(long acno, double amount) {
		ResponseStructure<bankAccount> structure=new ResponseStructure<>();
	bankAccount account=bankrep.findById(acno).get();
	account.setAmount(account.getAmount()+amount);
	
	banktrans.setDatatime(LocalDateTime.now());
	banktrans.setDeposit(amount);
	banktrans.setBalance(account.getAmount());
	
	List<bankTranscation> transcation=account.getBantranscation();
	transcation.add(banktrans);
		
	account.setBantranscation(transcation);
	
	structure.setCode(HttpStatus.ACCEPTED.value());
	structure.setMessgae("amount added sucessfully");
	structure.setData(bankrep.save(account));
	
	return structure;
	}

	public ResponseStructure<bankAccount> withdraw(long acno, double amount) throws myexception {
		ResponseStructure<bankAccount> structure=new ResponseStructure<>();
		bankAccount account=bankrep.findById(acno).get();
		if(amount>account.getAmount()) {
			throw new myexception("out of limit");
			
		}else {
			
			if(amount>account.getAmount()) {
			throw new myexception("insufficient funds");
		}
		  else {
		account.setAmount(account.getAmount()-amount);
		
		banktrans.setDatatime(LocalDateTime.now());
		banktrans.setDeposit(amount);
		banktrans.setBalance(account.getAmount());
		
		List<bankTranscation> transcation=account.getBantranscation();
		transcation.add(banktrans);
			
		account.setBantranscation(transcation);
		
		structure.setCode(HttpStatus.ACCEPTED.value());
		structure.setMessgae("amount added sucessfully");
		structure.setData(bankrep.save(account));
		  }
		}
		return structure;
	
}

	public ResponseStructure<List<bankTranscation>> viewtranscation(long acno) throws myexception {
	
		ResponseStructure<List<bankTranscation>> structure=new ResponseStructure<>();
		
		bankAccount account=bankrep.findById(acno).get();
		List<bankTranscation> list=account.getBantranscation();
		if(list.isEmpty()) {
			throw new myexception("no transcation");
		}else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessgae("data found");
			structure.setData(list);
		}
		return structure;
	}


}