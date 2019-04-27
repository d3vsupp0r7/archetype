package org.lba.springboot1.exception;


public class EmployeeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3140342400402453964L;
	
	public EmployeeNotFoundException(String exception) {
		super(exception);
	}

}