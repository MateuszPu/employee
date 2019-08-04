package net.guides.springboot2.springboot2jpacrudexample.employee;

import lombok.Getter;
import lombok.Setter;
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
class Employee {

	@Id
	private String id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phoneNumber;

	Employee(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	EmployeeView toView() {
		return new EmployeeView(id, firstName, lastName, email, phoneNumber);
	}
}
