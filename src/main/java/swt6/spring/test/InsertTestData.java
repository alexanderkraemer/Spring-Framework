package swt6.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.dao.*;
import swt6.spring.domain.*;
import swt6.spring.logic.WorkLogFacade;
import swt6.spring.ui.UIProcessFacade;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

@Component
public class InsertTestData {

    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private LogbookEntryRepository logbookEntryRepo;
    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private PhaseRepository phaseRepo;


    public void insert() {

        Employee pe1 = new PermanentEmployee("Markus", "Krämer", new Date(18, 01, 1991));
        Employee pe2 = new PermanentEmployee("Alexander", "Krämer", new Date(19, 02, 1992));
        Employee pe3 = new PermanentEmployee("Michaela", "Pilz", new Date(20, 03, 1993));
        Employee te4 = new TemporaryEmployee("Rebecca", "Krämer", new Date(21, 04, 1994));
        Employee te5 = new PermanentEmployee("Ute", "Krämer", new Date(22, 05, 1995));
        Employee te6 = new PermanentEmployee("Daniel", "Krämer", new Date(23, 06, 1996));


        employeeRepo.saveAndFlush(pe1);
        employeeRepo.saveAndFlush(pe2);
        employeeRepo.saveAndFlush(pe3);
        employeeRepo.saveAndFlush(te4);
        employeeRepo.saveAndFlush(te5);
        employeeRepo.saveAndFlush(te6);
/*
        Project p1 = new Project("project1", pe1);
        Project p2 = new Project("project2", pe2);
        Project p3 = new Project("project3", pe3);
        Project p4 = new Project("project4", te4);
        Project p5 = new Project("project5", te5);
        Project p6 = new Project("project6", te6);

        fc.addEmployeeToProject(pe1, p1);
        fc.addEmployeeToProject(pe2, p1);
        fc.addEmployeeToProject(pe3, p1);

        fc.syncProject(p1);
        fc.syncProject(p2);
        fc.syncProject(p3);
        fc.syncProject(p4);
        fc.syncProject(p5);
        fc.syncProject(p6);

        List<Issue> issues = new LinkedList<>();
        Issue i1 = new Issue(State.OPEN, Priority.HIGH, new Time(3, 10, 0), 10.0, p1);
        Issue i2 = new Issue(State.NEW, Priority.LOW, new Time(6, 0, 0), 50.0, p1);
        Issue i3 = new Issue(State.CLOSED, Priority.NORMAL, new Time(2, 10, 0), 40.0, p1);
        Issue i4 = new Issue(State.REJECTED, Priority.HIGH, new Time(1, 20, 0), 20.0, p1);
        Issue i5 = new Issue(State.NEW, Priority.NORMAL, new Time(1, 30, 0), 90.0, p1);
        Issue i6 = new Issue(State.OPEN, Priority.LOW, new Time(5, 50, 0), 80.0, p1);

        i1.setEmployee(pe1);
        i2.setEmployee(pe2);
        i3.setEmployee(pe3);
        i4.setEmployee(pe1);
        i5.setEmployee(pe2);
        i6.setEmployee(pe3);


        issues.add(i1);
        issues.add(i2);
        issues.add(i3);
        issues.add(i4);
        issues.add(i5);
        issues.add(i6);

        for(Issue issue: issues) {
            try {
                fc.syncIssue(issue);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Phase ph1 = new Phase("phase1");
        Phase ph2 = new Phase("phase2");
        Phase ph3 = new Phase("phase3");
        Phase ph4 = new Phase("phase4");
        Phase ph5 = new Phase("phase5");
        Phase ph6 = new Phase("phase6");

        fc.addPhase(ph1);
        fc.addPhase(ph2);
        fc.addPhase(ph3);
        fc.addPhase(ph4);
        fc.addPhase(ph5);
        fc.addPhase(ph6);


        LogbookEntry e1 = new LogbookEntry("activity1",
                //new Date(2017, 1, 1),
                //new Date(2017, 1, 2),
                new Time(10, 00, 00),
                new Time(11, 00, 00),
                pe1, ph1, i1);


        LogbookEntry e2 = new LogbookEntry("activity2",
//                new Date(2017, 2, 2),
//                new Date(2017, 2, 4),
                new Time(11, 00, 00),
                new Time(12, 00, 00),

                pe2, ph2, i2);


        LogbookEntry e3 = new LogbookEntry("activity3",
//                new Date(2017, 3, 8),
//                new Date(2017, 3, 4),
                new Time(13, 00, 00),
                new Time(14, 00, 00),

                pe3, ph3, i3);

        LogbookEntry e4 = new LogbookEntry("activity4",
//                new Date(2017, 4, 8),
//                new Date(2017, 4, 16),
                new Time(15, 00, 00),
                new Time(16, 00, 00),

                te4, ph4, i4);

        LogbookEntry e5 = new LogbookEntry("activity5",
//                new Date(2017, 5, 16),
//                new Date(2017, 5, 30),
                new Time(17, 00, 00),
                new Time(18, 00, 00),

                te5, ph5, i5);

        LogbookEntry e6 = new LogbookEntry("activity6",
//                new Date(2017, 5, 16),
//                new Date(2017, 5, 30),
                new Time(19, 00, 00),
                new Time(20, 00, 00),

                te6, ph6, i6);

        fc.syncLogbookEntry(e1);
        fc.syncLogbookEntry(e2);
        fc.syncLogbookEntry(e3);
        fc.syncLogbookEntry(e4);
        fc.syncLogbookEntry(e5);
        fc.syncLogbookEntry(e6);

        System.out.println("finished...");
        */
    }

}
