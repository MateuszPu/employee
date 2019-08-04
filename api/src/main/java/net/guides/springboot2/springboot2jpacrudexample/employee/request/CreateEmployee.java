package net.guides.springboot2.springboot2jpacrudexample.employee.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
public class CreateEmployee {

	@NotNull
	private final String firstName;
	@NotNull
	private final String lastName;
	@NotNull
	@Email
	private final String email;
	@NotNull
	private final String phoneNumber;

	@JsonCreator
	public CreateEmployee(@JsonProperty("firstName") String firstName,
						  @JsonProperty("lastName") String lastName,
						  @JsonProperty("email") String email,
						  @JsonProperty("phoneNumber") String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
