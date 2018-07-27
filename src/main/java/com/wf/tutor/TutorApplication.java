package com.wf.tutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.wf.tutor")
public class TutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorApplication.class, args);
	}
}
