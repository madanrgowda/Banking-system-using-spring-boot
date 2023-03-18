package com.bank.Rposiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
