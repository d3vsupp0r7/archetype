package org.lba.springboot1.rest.resource;

import java.util.List;

import org.lba.springboot1.db.model.Employee;
import org.lba.springboot1.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeRestResource {

	//C
	public ResponseEntity<Object> createEmployee(@RequestBody Employee employee);
	
	public ResponseEntity<Employee> returnCreatedEmployee(@RequestBody Employee employee);
	
	//R
	public List<Employee> retrieveAllEmployees();
	
	public Employee retrieveEmployeeById(@PathVariable long id) throws EmployeeNotFoundException;
	
	//U
	public ResponseEntity<Object> updateEmployeeById(@RequestBody Employee employee, @PathVariable long id) throws EmployeeNotFoundException;
	
	public ResponseEntity<Employee> returnUpdatedEmployeeById(@RequestBody Employee employee, @PathVariable long id) throws EmployeeNotFoundException;
	
	//D
	public void deleteEmployeeById(@PathVariable long id) throws EmployeeNotFoundException;
	
	public ResponseEntity<Employee> returnDeletedEmployee(@PathVariable("id") long id) throws EmployeeNotFoundException;
}
