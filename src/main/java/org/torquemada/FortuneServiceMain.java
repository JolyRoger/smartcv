package org.torquemada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class FortuneServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(FortuneServiceMain.class, args);
    }

}
