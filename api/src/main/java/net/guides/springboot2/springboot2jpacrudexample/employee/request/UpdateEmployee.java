package net.guides.springboot2.springboot2jpacrudexample.employee.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@EqualsAndHashCode
public class UpdateEmployee {

	@NotNull(message = "First name cannot be null")
	private final String firstName;
	@NotNull(message = "Last name cannot be null")
	private final String lastName;
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be a real email address")
	private final String email;
	@NotNull(message = "Phone number cannot be null")
	@Pattern(regexp = "^[2-9][0-9]\\d\\-?[2-9]([02-9]{2}|[02-9]\\d||\\d[02-9])\\-?\\d{4}$", message = "Phone number should be a real US phone number")
	private final String phoneNumber;

	@JsonCreator
	public UpdateEmployee(@JsonProperty("firstName") String firstName,
						  @JsonProperty("lastName") String lastName,
						  @JsonProperty("email") String email,
						  @JsonProperty("phoneNumber") String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
