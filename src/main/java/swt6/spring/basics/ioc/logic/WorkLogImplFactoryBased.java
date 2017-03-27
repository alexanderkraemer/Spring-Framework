package swt6.spring.basics.ioc.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.FileLogger;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

public class WorkLogImplFactoryBased {
	
	private Logger logger = null;

	private Map<Long, Employee> employees = new HashMap<Long, Employee>();

	private void init() {
		employees.put(1L, new Employee(1L, "Bill", "Gates"));
		employees.put(2L, new Employee(2L, "James", "Goslin"));
		employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
	}
	
	private void initLogger() {
		Properties props = new Properties();
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			props.load(cl.getResourceAsStream("swt6/spring/basics/logic/worklog.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		String type = props.getProperty("loggerType", "console");
		logger = LoggerFactory.getLogger(type);
	}

	public WorkLogImplFactoryBased() {
		initLogger();
		init();
	}

	public Employee findEmployeeById(Long id) {
		Employee empl = employees.get(id);
		logger.log("findEmployeeById(" + id + ") --> " + ((empl != null) ? empl.toString() : "<null>"));
		return employees.get(id);
	}

	public List<Employee> findAllEmployees() {
		logger.log("findAllEmployees");
		return new ArrayList<Employee>(employees.values());
	}
}
