package com.bank.Entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Component
public class bankTranscation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
	LocalDateTime datatime;
	double deposit;
	double balance;
}
