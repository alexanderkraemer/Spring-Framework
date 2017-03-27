package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DateUtil;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa.xml")) {

            EntityManagerFactory entityManagerFactory = factory.getBean(EntityManagerFactory.class);

            JpaUtil.executeInTransaction(entityManagerFactory, () -> {
                EmployeeRepository emplRepo = JpaUtil.getJpaRepository(entityManagerFactory, EmployeeRepository.class);
                Employee empl1 = new Employee("Josef", "Himmelbauer", DateUtil.getDate(1950, 1, 1));
                Employee empl2 = new Employee("Karl", "Malden", DateUtil.getDate(1940, 5, 3));

                System.out.println("----------------- save employee ----------------- ");
                empl1 = emplRepo.save(empl1);
                empl2 = emplRepo.save(empl2);
                emplRepo.flush();

                System.out.println("---------------- update employee ---------------- ");
                empl1.setFirstName("Franz");
                empl1 = emplRepo.save(empl1);

            });
            System.out.println("--------------------------------------------------");
            JpaUtil.executeInTransaction(entityManagerFactory, () -> {
                EmployeeRepository emplRepo = JpaUtil.getJpaRepository(entityManagerFactory, EmployeeRepository.class);

                System.out.println("---------------- findOne ---------------- ");
                System.out.println(emplRepo.findOne(1L));

                System.out.println("---------------- findAll ---------------- ");
                emplRepo.findAll().forEach(System.out::println);

                System.out.println("---------------- findByLastName ---------------- ");
                System.out.println(emplRepo.findByLastName("Malden"));

                System.out.println("------------ findByLastNameContaining ---------- ");
                emplRepo.findByLastNameContaining("Ma").forEach(System.out::println);

                System.out.println("---------------- findOlderThan ---------------- ");
                emplRepo.findByOlderThan(DateUtil.getDate(1945, 1, 1)).forEach(System.out::println);
            });
        }
    }

    public static void main(String[] args) {

        System.out.println("===========================================================");
        System.out.println("====================== testSpringData =====================");
        System.out.println("===========================================================");
        testSpringData();
    }
}
