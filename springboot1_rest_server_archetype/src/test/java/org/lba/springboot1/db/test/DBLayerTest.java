package org.lba.springboot1.db.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lba.springboot1.db.model.Employee;
import org.lba.springboot1.db.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DBLayerTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void whenFindByName_thenReturnEmployee() {
		// given
		Employee testEmployee = new Employee(9001L,"TestEmplyeeName", "TestEmployeeSurname");
		employeeRepository.save(testEmployee);

		// when
		Employee found = employeeRepository.findByName(testEmployee.getName());

		// then
		assertThat(found.getName()).isEqualTo(testEmployee.getName());
	}
}
