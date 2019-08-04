package net.guides.springboot2.springboot2jpacrudexample.employee;

import lombok.AllArgsConstructor;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.UpdateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class EmployeeFacade implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	@Override
	public ResponseEntity<List<EmployeeView>> findAll() {
		return ResponseEntity.ok(employeeRepository.findAll()
				.stream()
				.map(Employee::toView)
				.collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<EmployeeView> findById(String employeeId) {
		return ResponseEntity.of(employeeRepository.findById(employeeId).map(Employee::toView));
	}

	@Override
	public ResponseEntity<String> create(CreateEmployee request) {
		Employee employee = new Employee(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhoneNumber());
		Employee saved = employeeRepository.save(employee);
		return ResponseEntity.ok(saved.getId());
	}

	@Override
	public ResponseEntity<EmployeeView> update(String employeeId, UpdateEmployee updateEmployee) {
		throw new IllegalStateException("not umplemented");
	}

	@Override
	public ResponseEntity delete(String employeeId) {
		employeeRepository.deleteById(employeeId);
		return ResponseEntity.ok().build();
	}
}
