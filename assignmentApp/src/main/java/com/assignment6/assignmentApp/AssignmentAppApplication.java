package com.assignment6.assignmentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.assignment6.assignmentApp")
public class AssignmentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentAppApplication.class, args);
	}

}
