package org.example.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.logistica")
public class LogisticaApp {

    public static void main(String[] args) {
        SpringApplication.run(LogisticaApp.class, args);
    }
}
