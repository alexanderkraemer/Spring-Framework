package swt6.spring.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class PermanentEmployee extends Employee {

	private double salary;

	public PermanentEmployee() {}
	
	public PermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
		super(firstName, lastName, dateOfBirth);
	}
	
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", salary = " + salary;
	}
}
