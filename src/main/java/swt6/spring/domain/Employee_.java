package swt6.spring.domain;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employee.class)
public class Employee_ {
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, Date> dateOfBirth;
	public static volatile SingularAttribute<Employee, String> firstName;
	public static volatile SingularAttribute<Employee, String> lastName;
	public static volatile SingularAttribute<Employee, Address> address;
	public static volatile SetAttribute<Employee, LogbookEntry> logbookEntries;
	public static volatile SetAttribute<Employee, Project> projects;
}
