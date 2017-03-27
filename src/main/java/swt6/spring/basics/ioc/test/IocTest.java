package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.basics.ioc.WorkLogConfig;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.WorkLogImplFactoryBased;
import static swt6.util.PrintUtil.*;

public class IocTest {

	private static void testSimple() {
		WorkLogImplFactoryBased workLog = new WorkLogImplFactoryBased();
		workLog.findAllEmployees();
		workLog.findEmployeeById(3L);
	}

	private static void testXmlConfig() {
		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
				"swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")) {
			System.out.println("###> worklog-setter-injector");
			WorkLogFacade workLog1 = factory.getBean("worklog-setter-injected", WorkLogFacade.class);
			workLog1.findAllEmployees();
			workLog1.findEmployeeById(3L);
			
			System.out.println("###> worklog-constructor-injected");
			WorkLogFacade workLog2 = factory.getBean("worklog-constructor-injected", WorkLogFacade.class);
			workLog2.findAllEmployees();
			workLog2.findEmployeeById(3L);
		}
	}

	private static void testAnnotationConfig() {
		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
				"swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml")) {
			System.out.println("###> worklog-setter-injector");
			WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
			workLog.findAllEmployees();
			workLog.findEmployeeById(3L);

		}
	}

	private static void testJavaConfig() {
		try (AbstractApplicationContext factory = new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
			WorkLogFacade workLog = factory.getBean(WorkLogFacade.class);
			workLog.findAllEmployees();
			workLog.findEmployeeById(3L);
		}
	}

	public static void main(String[] args) {
//		printTitle("testSimple", 60);
//		testSimple();
//
//		printTitle("testXmlConfig", 60);
//		testXmlConfig();
//
//		printTitle("testAnnotationConfig", 60);
//		testAnnotationConfig();

		printTitle("testJavaConfig", 60);
		testJavaConfig();

		printSeparator(60);
	}
}
