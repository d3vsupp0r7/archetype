package org.lba.springboot1.rest.resource.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lba.springboot1.db.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class EmployeeRestResourceTests {

	@Autowired
	private MockMvc mvc;

	//Create
	@Test
	public void  whenCreateEmployee_createEmployee() throws Exception {
		String uri = "/employees/employee/";
		Employee product = new Employee();
		product.setId(3L);
		product.setName("TestEmployeeName_1");
		product.setSurname("TestEmployeeSurname_1");

		String inputJson = mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.length() == 0);
	}

	@Test
	public void  whenCreateEmployee_returnCreatedEmployee() throws Exception {
		String uri = "/employees/employee/created";
		Employee product = new Employee();
		product.setId(3L);
		product.setName("TestEmployeeName_1");
		product.setSurname("TestEmployeeSurname_1");

		String inputJson = mapToJson(product);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee employee = mapFromJson(content, Employee.class);
		assertNotNull(employee);
	}

	//Read
	@Test
	public void whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
		String uri = "/employees/";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee[] employeeArray = mapFromJson(content, Employee[].class);
		assertTrue(employeeArray.length > 0);
	}

	@Test
	public void whenGetEmployeeById_thenReturnSingleEmployee() throws Exception {
		String uri = "/employees/employee/10001";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee employee = mapFromJson(content, Employee.class);
		assertNotNull(employee);
	}
	
	@Test
	public void whenGetEmployeeByIdNotPresent_thenReturnException() throws Exception {
		String uri = "/employees/employee/20001";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Employee Record Not Found"));
	}
	
	@Test
    public void whenGetEmployeeByIdNotPresent_thenReturnException_with_MockMvc() {
        try {
        	mvc.perform(get("/employees/employee/20001"))
                   .andExpect(status().isNotFound())
                   .andExpect(content().string(containsString("Employee Record Not Found")))
                   .andDo(print());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

	//Update
	@Test
	public void  whenUpdateEmployeeById_updateEmployee() throws Exception {
		String uri = "/employees/employee/10001";
		Employee employee = new Employee();
		employee.setName("EmployeeName_1_Updated");
		employee.setSurname("EmployeeSurname_1_Updated");
		String inputJson = mapToJson(employee);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.length() == 0);
	}
	
	@Test
	public void  whenUpdateEmployeeById_returnUpdatedEmployee() throws Exception {
		String uri = "/employees/employee/updated/10001";
		Employee employee = new Employee();
		employee.setName("EmployeeName_1_Updated");
		employee.setSurname("EmployeeSurname_1_Updated");
		String inputJson = mapToJson(employee);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee updatedEmployee = mapFromJson(content, Employee.class);
		assertNotNull(updatedEmployee);
	}

	//Delete
	@Test
	public void whenDeleteEmployee_deleteEmployee() throws Exception {
		String uri = "/employees/employee/90001";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.length() == 0);
	}

	@Test
	public void whenDeleteEmployee_returnDeletedEmployee() throws Exception {
		String uri = "/employees/employee/delete/90002";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee employee = mapFromJson(content, Employee.class);
		assertNotNull(employee);
	}
	
	@Test
	public void whenDeleteEmployeeByIdNotPresent_thenReturnException() throws Exception {
		String uri = "/employees/employee/delete/20001";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(404, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains("Unable to find employee to delete with id"));
	}

	/* Utils */
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
