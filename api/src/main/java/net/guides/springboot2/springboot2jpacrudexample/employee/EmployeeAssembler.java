package net.guides.springboot2.springboot2jpacrudexample.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeAssembler {

	@Bean
	public EmployeeService employeeService(EmployeeRepository employeeRepository) {
		return new EmployeeFacade(employeeRepository);
	}
}
