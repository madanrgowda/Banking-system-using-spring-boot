package com.bank.Rposiory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.Entity.Customer;
import com.bank.Entity.Management;
@Repository
public interface ManaementRepository extends JpaRepository<Management, Integer>{

	Management findByEmail(String email);

}
