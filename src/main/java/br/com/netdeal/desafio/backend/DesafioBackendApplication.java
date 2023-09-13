package br.com.netdeal.desafio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DesafioBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioBackendApplication.class, args);
    }

}
