package org.lba.springboot1.rest.resource.impl;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.lba.springboot1.db.model.Employee;
import org.lba.springboot1.db.repository.EmployeeRepository;
import org.lba.springboot1.exception.EmployeeNotFoundException;
import org.lba.springboot1.rest.resource.EmployeeRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("employees")
public class EmployeeRestResourceImpl implements EmployeeRestResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestResourceImpl.class);	

	@Autowired
	private EmployeeRepository employeeRepository;

	//Create
	@PostMapping("/employee")
	public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {

		LOGGER.debug("*** START createEmployee ***");
		Employee savedEmployee = employeeRepository.save(employee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedEmployee.getId()).toUri();

		LOGGER.debug("*** END createEmployee ***");
		return ResponseEntity.created(location).build();

	}

	@PostMapping("/employee/created")
	public ResponseEntity<Employee> returnCreatedEmployee(@RequestBody Employee employee) {

		LOGGER.debug("*** START returnCreatedEmployee ***");
		Employee savedEmployee = employeeRepository.save(employee);

		LOGGER.debug("*** END returnCreatedEmployee ***");

		return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
	}

	//Read
	@GetMapping("/")
	public List<Employee> retrieveAllEmployees() {
		LOGGER.debug("*** START retrieveAllEmployees ***");
		return employeeRepository.findAll();
	}

	@GetMapping("/employee/{id}")
	public Employee retrieveEmployeeById(@PathVariable long id) throws EmployeeNotFoundException {

		LOGGER.debug("*** START retrieveEmployeeById: " + "id: " + id + " ***");
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent()) {
			LOGGER.debug("*** No employee with id: " + id + " was found ***");
			throw new EmployeeNotFoundException("id-" + id);
		}

		LOGGER.debug("*** END retrieveEmployeeById - id: " + id + " ***");
		return employee.get();
	}

	//Update
	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployeeById(@RequestBody Employee employee, @PathVariable long id) throws EmployeeNotFoundException {

		LOGGER.debug("*** START updateEmployeeById - id: {0}  ***",id);
		Optional<Employee> employeeOptional = employeeRepository.findById(id);

		if (!employeeOptional.isPresent()) {
			LOGGER.debug("Unable to update. Employee with id " + id + " not found");
			throw new EmployeeNotFoundException("Unable to find employee to update with id - " + id);
		}

		employee.setId(id);

		employeeRepository.save(employee);

		LOGGER.debug("*** END updateEmployeeById ***");
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/employee/updated/{id}")
	public ResponseEntity<Employee> returnUpdatedEmployeeById(@RequestBody Employee employee, @PathVariable long id) throws EmployeeNotFoundException {

		LOGGER.debug("*** START returnUpdatedEmployeeById - id: {0}  ***",id);
		Optional<Employee> employeeOptional = employeeRepository.findById(id);

		if (!employeeOptional.isPresent()) {
			LOGGER.debug("Unable to update. Employee with id " + id + " not found");
			throw new EmployeeNotFoundException("Unable to find employee to update with id - " + id);
		}

		employee.setId(id);

		Employee savedEmployee = employeeRepository.save(employee);

		LOGGER.debug("*** END returnUpdatedEmployeeById ***");

		return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
	}

	//Delete
	@DeleteMapping("/employee/{id}")
	public void deleteEmployeeById(@PathVariable long id) throws EmployeeNotFoundException {

		LOGGER.debug("*** START deleteEmployeeById - id: " + id + " ***");
		
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee == null || !employee.isPresent()) {
			LOGGER.debug("Unable to delete. Employee with id " + id + " not found");
			throw new EmployeeNotFoundException("Unable to find employee to delete with id - " + id);
		}
		
		employeeRepository.delete(id);
	}

	@DeleteMapping("/employee/delete/{id}")
	public ResponseEntity<Employee> returnDeletedEmployee(@PathVariable("id") long id) throws EmployeeNotFoundException {
		LOGGER.debug("*** START returnDeletedEmployee - id: " + id + " ***");

		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee == null || !employee.isPresent()) {
			LOGGER.debug("Unable to delete. Employee with id " + id + " not found");
			throw new EmployeeNotFoundException("Unable to find employee to delete with id - " + id);
		}

		employeeRepository.delete(id);
		LOGGER.debug("*** END returnDeletedEmployee ***");
		Employee foundEmployee = employee.get();

		return new ResponseEntity<>(foundEmployee,HttpStatus.OK);
	}

}
