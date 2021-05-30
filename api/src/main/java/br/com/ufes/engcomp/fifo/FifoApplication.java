package br.com.ufes.engcomp.fifo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FifoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FifoApplication.class, args);
		
		System.out.println("Server is running!");
	}

}
