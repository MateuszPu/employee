package net.guides.springboot2.springboot2jpacrudexample.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Employee {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;

	public Employee() {

	}

	public Employee(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

}
