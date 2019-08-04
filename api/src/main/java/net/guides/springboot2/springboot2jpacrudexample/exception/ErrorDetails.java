package net.guides.springboot2.springboot2jpacrudexample.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
	private LocalDateTime dateTime;
	private String message;
	private String details;
}
