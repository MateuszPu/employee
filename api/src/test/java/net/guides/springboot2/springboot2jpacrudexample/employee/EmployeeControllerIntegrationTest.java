package net.guides.springboot2.springboot2jpacrudexample.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.request.UpdateEmployee;
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerIntegrationTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	private String rootUrl() {
		return "/api/v1/employees";
	}

	@Test
	public void testGetAllEmployees() throws Exception {
		//given
		when(employeeService.findAll()).thenReturn(ResponseEntity.ok(new ArrayList<>()));

		//when
		ResultActions perform = this.mockMvc.perform(get(rootUrl()));

		//then
		perform
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json("[]"));
	}

	@Test
	public void shouldReturn404WhenEmployeeDoesNotExist() throws Exception {
		//given
		final String EMPLOYEE_ID = "employ-id";
		when(employeeService.findById(EMPLOYEE_ID)).thenReturn(ResponseEntity.of(Optional.empty()));

		//when
		ResultActions perform = this.mockMvc.perform(get(rootUrl() + "/" + EMPLOYEE_ID));

		//then
		perform
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnIdOfNewCreatedEmployee() throws Exception {
		//given
		final String EMPLOYEE_ID = "employee-id";
		ObjectMapper mapper = new ObjectMapper();
		CreateEmployee request = new CreateEmployee("firstName", "lasName", "Email@as.pl", "123435");
		when(employeeService.create(request)).thenReturn(ResponseEntity.ok(EMPLOYEE_ID));

		//when
		ResultActions perform = this.mockMvc.perform(post(rootUrl())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)));

		//then
		perform
			.andExpect(status().isOk())
			.andExpect(content().string(EMPLOYEE_ID));

	}

	@Test
	public void shouldReturnUpdatedEmployeeAfterUpdate() throws Exception {
		//given
		final String EMPLOYEE_ID = "employee-id";
		final String FIRST_NAME = "firstName";
		final String LAST_NAME = "lasName";
		final String EMAIL = "Email@as.pl";
		final String PHONE_NUMBER = "123435";
		ObjectMapper mapper = new ObjectMapper();
		UpdateEmployee request = new UpdateEmployee(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER);
		EmployeeView response = new EmployeeView(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER);
		when(employeeService.update(EMPLOYEE_ID, request)).thenReturn(ResponseEntity.ok(response));

		//when
		ResultActions perform = this.mockMvc.perform(put(rootUrl()+"/"+EMPLOYEE_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)));

		//then
		perform
			.andExpect(status().isOk())
			.andExpect(content().string(mapper.writeValueAsString(response)));
	}

	@Test
	public void shouldReturnStatusOkWhenDeleteEmployee() throws Exception {
		//given
		final String EMPLOYEE_ID = "employee-id";
		when(employeeService.delete(EMPLOYEE_ID)).thenReturn(ResponseEntity.ok().build());

		//when
		ResultActions perform = this.mockMvc.perform(delete(rootUrl()+"/"+EMPLOYEE_ID));

		//then
		perform
				.andExpect(status().isOk());
	}
}
