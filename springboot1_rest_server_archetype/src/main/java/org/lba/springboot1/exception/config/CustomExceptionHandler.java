package org.lba.springboot1.exception.config;

import java.util.ArrayList;
import java.util.List;

import org.lba.springboot1.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(EmployeeNotFoundException ex, WebRequest request) {

		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Employee Record Not Found", details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}
}
