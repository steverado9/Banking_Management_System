package com.steverado9.Banking.Management.System;

import com.steverado9.Banking.Management.System.entity.User;
import com.steverado9.Banking.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BankingManagementSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementSystemApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User(
			"Isaac Stephen", "steverado9", "stephen123", "isaac.stephen@gmail.com", "08012345678", "ADMIN", "15 Broad Street Lagos",  LocalDate.of(1995, 7, 14), "Software Engineer", "123456789", LocalDate.of(2025, 10, 20), null
		);
		userRepository.save(user1);
	}
}
