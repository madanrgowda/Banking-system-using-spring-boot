package com.bank.Rposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.Entity.bankAccount;

@Repository
public interface Bankrepository  extends JpaRepository<bankAccount, Long>{

}
