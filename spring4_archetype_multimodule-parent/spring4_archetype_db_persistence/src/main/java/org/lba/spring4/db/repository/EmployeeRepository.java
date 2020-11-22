package org.lba.spring4.db.repository;

import java.util.List;

import org.lba.spring4.db.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	Employee findById(Long id);

	Employee findByName(String name);

	/** JPA External query on properties file **/
	@Query(name = "Employee.findAllNamedExternalFileJPAQuery")
	List<Employee> findAllNamedExternalFileJPAQuery();

	@Query(name = "Employee.findByNameNamedExternalFileJPAQuery")
	List<Employee> findByNameNamedExternalFileJPAQuery(String name);

	/** Native External query on properties file **/
	@Query(nativeQuery = true)
	public List<Employee> findAllNameNativeQueryExternalFile();
	
	@Query(nativeQuery = true)
	public List<Employee> findByNameNativeQueryExternalFile(@Param("name") String name);

}