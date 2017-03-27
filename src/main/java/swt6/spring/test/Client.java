package swt6.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.domain.*;
import swt6.spring.ui.UIProcessFacade;
import swt6.spring.logic.WorkLogFacade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Date;


public class Client {
    @Autowired
    private static WorkLogFacade fc;
    @Autowired
    private static UIProcessFacade cp;

    private static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    public static void main(String[] args) {
        AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "swt6/spring/test/applicationContext-jdbc.xml");
        fc = appCtx.getBean(WorkLogFacade.class);
        cp = appCtx.getBean(UIProcessFacade.class);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: quit, insert, list";

        System.out.println("Hibernate Employee Admin");
        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {

            // System.out.println("create schema ...");

            while (!userCmd.equals("quit")) {
                switch (userCmd) {
                    case "list employees":
                        cp.listEmployees();
                        break;
                    case "create project":
                        cp.createProject(new Project(
                                promptFor(in, "Projektname"),
                                fc.findEmployeeById(Long.parseLong(promptFor(in, "EmployeeID")))
                        ));
                        break;
                    case "put employee to project":
                        cp.putEmployeeToProject(
                                Long.parseLong(promptFor(in, "EmployeeID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "remove employee from project":
                        cp.removeEmployeeFromProject(
                                Long.parseLong(promptFor(in, "EmployeeID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list employees from project":
                        cp.listEmployeesFromProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "create issue":
                        cp.createIssue(new Issue(
                                State.valueOf(promptFor(in, "State(NEW; OPENED, REJECTED, CLOSED)")),
                                Priority.valueOf(promptFor(in, "Priority (HIGH; LOW; NORMAL)")),
                                new Time(
                                        Integer.parseInt(promptFor(in, "Hours")),
                                        Integer.parseInt(promptFor(in, "Minutes")),
                                        Integer.parseInt(promptFor(in, "Seconds"))
                                ),
                                Integer.parseInt(promptFor(in, "Finished in %"))/100,
                                fc.findProjectById(Long.parseLong(promptFor(in, "ProjectID")))
                        ));
                        break;
                    case "update issue":
                        System.out.println("Leave properties empty, if you don't want to change them!!");
                        Long issueId = Long.parseLong(promptFor(in, "IssueID"));

                        cp.updateIssue(issueId, new Issue(
                                State.valueOf(promptFor(in, "State(NEW; OPENED, REJECTED, CLOSED)")),
                                Priority.valueOf(promptFor(in, "Priority (HIGH; LOW; NORMAL)")),
                                new Time(
                                        Integer.parseInt(promptFor(in, "Estimated Hours")),
                                        Integer.parseInt(promptFor(in, "Estimated Minutes")),
                                        Integer.parseInt(promptFor(in, "Estimated Seconds"))
                                ),
                                Integer.parseInt(promptFor(in, "Finished in %"))/100,
                                fc.findProjectById(Long.parseLong(promptFor(in, "ProjectID")))
                        ));
                        break;
                    case "put issue to project":
                        cp.putIssueToProject(
                                Long.parseLong(promptFor(in, "IssueID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project":
                        cp.listIssuesFromProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project by status":
                        cp.listIssuesFromProjectByStatus(
                                Long.parseLong(promptFor(in, "ProjectID")),
                                State.valueOf(promptFor(in, "State(NEW; OPENED, REJECTED, CLOSED)"))
                        );
                        break;
                    case "list issues from project grouped by employee":
                        cp.listIssuesFromProjectGroupedByEmployee(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project grouped by employee by status":
                        cp.listIssuesFromProjectGroupedByEmployeeByStatus(
                                Long.parseLong(promptFor(in, "ProjectID")),
                                State.valueOf(promptFor(in, "State(NEW; OPENED, REJECTED, CLOSED)"))
                        );
                        break;
                    case "list worked time by project":
                        cp.listWorkedTimeByProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list phases":
                        cp.listPhases();
                        break;
                    case "create Logbookentry":
                        cp.addLogbookEntry(
                                new LogbookEntry(
                                        promptFor(in, "Activity"),
                                        new Time(
                                                Integer.parseInt(promptFor(in, "Starttime: Hours")),
                                                Integer.parseInt(promptFor(in, "Starttime: Minutes")),
                                                Integer.parseInt(promptFor(in, "Starttime: Seconds"))
                                        ),
                                        new Time(
                                                Integer.parseInt(promptFor(in, "Endtime: Hours")),
                                                Integer.parseInt(promptFor(in, "Endtime: Minutes")),
                                                Integer.parseInt(promptFor(in, "Endtime: Seconds"))
                                        ),
                                        fc.findEmployeeById(Long.parseLong(promptFor(in, "EmployeeID"))),
                                        fc.findPhaseById(Long.parseLong(promptFor(in, "Phase"))),
                                        fc.findIssueById(Long.parseLong(promptFor(in, "IssueID")))
                                )
                        );
                        break;
                    case "quit":

                        break;

                    default:
                        System.err.printf("Invalid Command [%s]%n", userCmd);
                        break;

                } // switch
                System.out.println(availCmds);
                userCmd = promptFor(in, "");

            } // while
        } finally {

        }

    }
}
