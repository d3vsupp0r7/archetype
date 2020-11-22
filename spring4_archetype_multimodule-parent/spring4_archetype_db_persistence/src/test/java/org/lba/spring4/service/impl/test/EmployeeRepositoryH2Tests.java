package org.lba.spring4.service.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lba.spring4.db.model.Employee;
import org.lba.spring4.db.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring-test-database-h2-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeRepositoryH2Tests {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void whenListAllEmployees_returnThreeElements() {

		List<Employee> listAllEmployee = employeeRepository.findAll();
		assertNotNull(listAllEmployee);
		assertEquals(3, listAllEmployee.size());
	}
	
	@Test
	public void whenGetEmployeeById_returnFoundEmployee() {

		Employee employee = employeeRepository.findById(1L);
		assertNotNull(employee);
		assertEquals("ASampleName001", employee.getName());
	}
	
	@Test
	public void whenSaveNewEmployee_returnSavedEmployee() {

		Employee employee = new Employee("JUnitEmployeeName","JUnitEmployeeSurname");
		Employee savedEmployee = employeeRepository.save(employee);
		assertNotNull(savedEmployee);
		assertEquals("JUnitEmployeeName", savedEmployee.getName());
	}
	
}
