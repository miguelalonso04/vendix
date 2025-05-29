package com.miguel.vendix;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VendixApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendixApplication.class, args);
	}

    @Bean
    ApplicationRunner delaySchedulerStart() {
        return args -> {
            // El scheduler empezará después de 10 segundos
            Thread.sleep(10000);
        };
    }

}
