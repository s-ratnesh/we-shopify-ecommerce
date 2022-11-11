package com.weshopify.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class WeShopifyPlatformApplication implements CommandLineRunner{

	@Autowired
	private HikariDataSource hds;
	
	public static void main(String[] args) {
		SpringApplication.run(WeShopifyPlatformApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("command line runner ru methos is executing....");
		System.out.println(hds.getDriverClassName());
		System.out.println(hds.getUsername());
		System.out.println(hds.getJdbcUrl());
		System.out.println(hds.getPassword());
		System.out.println(hds.getSchema());
	}
	
}
