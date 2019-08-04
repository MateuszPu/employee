package net.guides.springboot2.springboot2jpacrudexample.employee

import com.fasterxml.jackson.databind.ObjectMapper
import net.guides.springboot2.springboot2jpacrudexample.employee.request.CreateEmployee
import net.guides.springboot2.springboot2jpacrudexample.employee.request.UpdateEmployee
import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [EmployeeController])
class EmployeeControllerIntegrationTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private EmployeeService employeeService

    private static String rootUrl() {
        return "/api/v1/employees"
    }

    def 'when user get all employee, should return empty list when database is empty'() {
        given:
        employeeService.findAll() >> ResponseEntity.ok(new ArrayList<>())

        when:
        def result = this.mockMvc.perform(get(rootUrl()))

        then:
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"))
    }

    def 'when user get particular employee by id, should return 404 no content when employee does not exist'() {
        given:
        final String EMPLOYEE_ID = "employ-id"
        employeeService.findById(EMPLOYEE_ID) >> ResponseEntity.of(Optional.empty())

        when:
        ResultActions result = this.mockMvc.perform(get(rootUrl() + "/" + EMPLOYEE_ID))

        then:
        result
                .andExpect(status().isNotFound())
    }

    def 'when user create new employee, should return id of new created employee'() {
        given:
        final String EMPLOYEE_ID = "employee-id"
        final String FIRST_NAME = "firstName"
        final String LAST_NAME = "lasName"
        final String EMAIL = "Email@as.pl"
        final String PHONE_NUMBER = "585-255-0555"
        ObjectMapper mapper = new ObjectMapper()
        CreateEmployee request = new CreateEmployee(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER)
        employeeService.create(request) >> ResponseEntity.ok(EMPLOYEE_ID)

        when:
        ResultActions result = this.mockMvc.perform(post(rootUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

        then:
        result
                .andExpect(status().isOk())
                .andExpect(content().string(EMPLOYEE_ID))
    }

    def 'when user update existing employee, should return updated employee'() {
        given:
        final String EMPLOYEE_ID = "employee-id"
        final String FIRST_NAME = "firstName"
        final String LAST_NAME = "lasName"
        final String EMAIL = "Email@as.pl"
        final String PHONE_NUMBER = "585-255-0555"
        ObjectMapper mapper = new ObjectMapper()
        UpdateEmployee request = new UpdateEmployee(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER)
        EmployeeView response = new EmployeeView(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER)
        employeeService.update(EMPLOYEE_ID, request) >> ResponseEntity.ok(response)

        when:
        ResultActions result = this.mockMvc.perform(put(rootUrl() + "/" + EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

        then:
        result
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(response)))
    }

    def 'when user delete any employee, should return ok status'() {
        given:
        final String EMPLOYEE_ID = "employee-id"
        employeeService.delete(EMPLOYEE_ID) >> ResponseEntity.ok().build()

        when:
        ResultActions result = this.mockMvc.perform(delete(rootUrl() + "/" + EMPLOYEE_ID))

        then:
        result
                .andExpect(status().isOk())
    }

    @Unroll
    def 'should return bad request when #request when #description'() {
        when:
        ResultActions result = this.mockMvc.perform(post(rootUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))

        then:
        result
                .andExpect(status().isBadRequest())

        where:
        description                                       | request
        "phoneNumber is null"                             | createEmployeeRequest("firstName", "lastName", "email@pcz.pl", null)
        "phoneNumber 185-255-0555 is invalid phoneNumber" | createEmployeeRequest("firstName", "lastName", "invalid", "185-255-0555")
        "phoneNumber 085-255-0555 is invalid phoneNumber" | createEmployeeRequest("firstName", "lastName", "invalid", "085-255-0555")
        "email is null"                                   | createEmployeeRequest("firstName", "lastName", null, "585-255-0555")
        "lastName is null"                                | createEmployeeRequest("firstName", null, "email@pcz.pl", "585-255-0555")
        "firstName is null"                               | createEmployeeRequest(null, "lastName", "email@pcz.pl", "585-255-0555")
        "email invalid@ is invalid address email"         | createEmployeeRequest("firstName", "lastName", "invalid@", "585-255-0555")
        "email invalid is invalid address email"          | createEmployeeRequest("firstName", "lastName", "invalid", "585-255-0555")

    }

    private String createEmployeeRequest(def firstName, def lastName, def email, def phoneNumber) {
        ObjectMapper mapper = new ObjectMapper()
        return mapper.writeValueAsString(new CreateEmployee(firstName, lastName, email, phoneNumber))
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        EmployeeService employeeService() {
            return detachedMockFactory.Stub(EmployeeService)
        }
    }
}
