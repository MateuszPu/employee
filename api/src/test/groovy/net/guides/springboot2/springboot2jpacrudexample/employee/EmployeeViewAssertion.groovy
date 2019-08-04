package net.guides.springboot2.springboot2jpacrudexample.employee

import net.guides.springboot2.springboot2jpacrudexample.employee.view.EmployeeView
import org.assertj.core.api.AbstractAssert

import static org.assertj.core.api.Assertions.assertThat

class EmployeeViewAssertion extends AbstractAssert<EmployeeViewAssertion, EmployeeView> {

    private EmployeeViewAssertion(EmployeeView employeeView) {
        super(employeeView, EmployeeViewAssertion.class);
    }

    public static EmployeeViewAssertion assertEmployeeView(EmployeeView employeeView) {
        return new EmployeeViewAssertion(employeeView)
    }

    public EmployeeViewAssertion hasFirstName(String firstName) {
        assertThat(actual.getFirstName()).isEqualTo(firstName)
        return this
    }

    public EmployeeViewAssertion hasLastName(String lastName) {
        assertThat(actual.getLastName()).isEqualTo(lastName)
        return this
    }

    public EmployeeViewAssertion hasEmail(String email) {
        assertThat(actual.getEmail()).isEqualTo(email)
        return this
    }

    public EmployeeViewAssertion hasPhoneNumber(String phoneNumber) {
        assertThat(actual.getPhoneNumber()).isEqualTo(phoneNumber)
        return this
    }

    public EmployeeViewAssertion hasId() {
        assertThat(actual.getId()).isNotNull()
        return this
    }
}
