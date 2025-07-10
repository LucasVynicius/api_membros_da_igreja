package br.com.gerenciadordemembros.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.gerenciadordemembros.api")
public class GerenciamentoDeMembrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoDeMembrosApplication.class, args);
	}

}
