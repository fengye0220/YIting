package com.example.music1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Music1Application {

    public static void main(String[] args) {
        SpringApplication.run(Music1Application.class, args);
    }

}

