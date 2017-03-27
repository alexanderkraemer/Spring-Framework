package swt6.spring.worklog.test;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.ui.UIProcessFacade;
import swt6.util.DateUtil;

public class UITest {

	private static void uiTest(String configFile) {

		Employee empl1 = new Employee("Sepp", "Forcher", DateUtil.getDate(1935, 12, 12));
		Employee empl2 = new Employee("Alfred", "Kunz", DateUtil.getDate(1944, 8, 10));
		Employee empl3 = new Employee("Sigfried", "Hinz", DateUtil.getDate(1954, 5, 3));

//		LogbookEntry entry1 = new LogbookEntry("Analyse", DateUtil.getTime(10, 0), DateUtil.getTime(13, 45));
//		LogbookEntry entry2 = new LogbookEntry("Implementierung", DateUtil.getTime(10, 15), DateUtil.getTime(14, 30));
//		LogbookEntry entry3 = new LogbookEntry("Testen", DateUtil.getTime(10, 15), DateUtil.getTime(14, 30));

//		try (AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile)) {
//
//			UIProcessFacade uiComp = appCtx.getBean(UIProcessFacade.class);
//
//			printTitle("saveEmployees", 60, '-');
//			uiComp.saveEmployees(empl1, empl2, empl3);
//
//			empl1.addLogbookEntry(entry1);
//			empl1.addLogbookEntry(entry2);
//			empl2.addLogbookEntry(entry3);
//
//			printTitle("saveEmployees", 60, '-');
//			uiComp.saveEmployees(empl1, empl2);
//
//			printTitle("findById", 60, '-');
//			uiComp.findById(1L);
//
//			printTitle("findAll", 60, '-');
//			uiComp.findAll();
//
//		}
	}

	public static void main(String[] args) {
		printSeparator(60);
		printTitle("UITest (JPA)", 60);
		printSeparator(60);
		uiTest("swt6/spring/worklog/test/applicationContext-jpa1.xml");
	}
}
