package net.guides.springboot2.springboot2jpacrudexample.employee;

import lombok.AllArgsConstructor;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.UpdateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
class EmployeeController {

	private final EmployeeService employeeService;

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeView>> getAllEmployees() {
		return employeeService.findAll();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeView> getEmployeeById(@PathVariable(value = "id") String employeeId) {
		return employeeService.findById(employeeId);
	}

	@PostMapping("/employees")
	public ResponseEntity<String> createEmployee(@Valid @RequestBody CreateEmployee request) {
		return employeeService.create(request);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeView> updateEmployee(@PathVariable(value = "id") String employeeId,
													   @Valid @RequestBody UpdateEmployee updateEmployee) {
		return employeeService.update(employeeId, updateEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity deleteEmployee(@PathVariable(value = "id") String employeeId) {
		return employeeService.delete(employeeId);
	}
}
