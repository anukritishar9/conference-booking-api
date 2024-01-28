package com.demo.conferenceRoomApp;

import com.demo.conferenceRoomApp.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
@EnableAutoConfiguration

public class ConferenceRoomApp {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceRoomApp.class, args);
	}

}
