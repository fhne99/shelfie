package com.projet.shelfie;

import org.springframework.boot.SpringApplication;

public class TestShelfieApplication {

	public static void main(String[] args) {
		SpringApplication.from(ShelfieApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
