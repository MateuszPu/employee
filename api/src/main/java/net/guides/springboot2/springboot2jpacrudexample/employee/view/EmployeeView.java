package net.guides.springboot2.springboot2jpacrudexample.employee.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EmployeeView {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

}
