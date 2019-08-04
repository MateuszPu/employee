package net.guides.springboot2.springboot2jpacrudexample.employee;

import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.UpdateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
	ResponseEntity<List<EmployeeView>> findAll();

	ResponseEntity<EmployeeView> findById(String employeeId);

	ResponseEntity<String> create(CreateEmployee request);

	ResponseEntity<EmployeeView> update(String employeeId, UpdateEmployee updateEmployee);

	ResponseEntity delete(String employeeId);
}
