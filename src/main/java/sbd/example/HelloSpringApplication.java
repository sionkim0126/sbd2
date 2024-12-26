package sbd.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

    public static void main(String[] args) {
        // SpringApplication.run을 호출하여 애플리케이션을 실행합니다.
        SpringApplication.run(HelloSpringApplication.class, args);
    }
}