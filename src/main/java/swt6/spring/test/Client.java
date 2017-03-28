package swt6.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.dao.EmployeeRepositoryCustom;
import swt6.spring.dao.EmployeeRepositoryImpl;
import swt6.spring.domain.*;
import swt6.spring.ui.UIProcessFacade;
import swt6.spring.logic.WorkLogFacade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.*;


public class Client {
    @Autowired
    private static WorkLogFacade fc;
    @Autowired
    private static UIProcessFacade cp;
    @Autowired
    private static InsertTestData id;

    private static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    private static void printTitle(String msg){
        System.out.println("======================== " + msg);
    }

    public static void main(String[] args) {
        AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "swt6/spring/test/applicationContext-jdbc.xml");
        fc = appCtx.getBean(WorkLogFacade.class);
        cp = appCtx.getBean(UIProcessFacade.class);
        id = appCtx.getBean(InsertTestData.class);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<String> commands = new LinkedList<>();
        commands.add("help");
        commands.add("insert data");
        commands.add("list employees");
        commands.add("create project");
        commands.add("put employee to project");
        commands.add("remove employee from project");
        commands.add("list employees from project");
        commands.add("create issue");
        commands.add("update issue");
        commands.add("put issue to project");
        commands.add("needed time per employee of project");
        commands.add("get issues for employee");
        commands.add("list issues from project");
        commands.add("list issues from project by status");
        commands.add("list issues from project grouped by employee");
        commands.add("list issues from project grouped by employee by status");
        commands.add("list worked time by project");
        commands.add("list phases");
        commands.add("create Logbookentry");
        commands.add("quit");


        String help = "Type help to see all available commands!";

        System.out.println("Admin Client");
        System.out.println(help);
        String userCmd = promptFor(in, "");

        try {

            // System.out.println("create schema ...");

            while (!userCmd.equals("quit")) {
                switch (userCmd) {
                    case "help":
                        printTitle("help");
                        commands.forEach(System.out::println);
                        break;
                    case "insert data":
                        printTitle("insert data");
                        id.insert();
                        break;
                    case "list employees":
                        printTitle("list employees");
                        cp.listEmployees();
                        break;
                    case "create project":
                        printTitle("create project");
                        Project p = cp.createProject(new Project(
                                promptFor(in, "Projektname"),
                                fc.findEmployeeById(Long.parseLong(promptFor(in, "EmployeeID")))
                        ));
                        System.out.println(p);
                        break;
                    case "put employee to project":
                        printTitle("put employee to project");
                        cp.putEmployeeToProject(
                                Long.parseLong(promptFor(in, "EmployeeID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "remove employee from project":
                        printTitle("remove employee from project");
                        cp.removeEmployeeFromProject(
                                Long.parseLong(promptFor(in, "EmployeeID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list employees from project":
                        printTitle("list employees from project");
                        cp.listEmployeesFromProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "get issues for employee":
                        printTitle("get issues for employee");
                        cp.getIssuesForEmployee(
                                Long.parseLong(promptFor(in, "EmployeeID")),
                                State.valueOf(promptFor(in, "State(NEW; OPEN, REJECTED, CLOSED)"))
                        );
                        break;
                    case "create issue":
                        printTitle("create issue");
                        Issue i = cp.createIssue(new Issue(
                                State.valueOf(promptFor(in, "State(NEW; OPEN, REJECTED, CLOSED)")),
                                Priority.valueOf(promptFor(in, "Priority (HIGH; LOW; NORMAL)")),
                                new Time(
                                        Integer.parseInt(promptFor(in, "Hours")),
                                        Integer.parseInt(promptFor(in, "Minutes")),
                                        Integer.parseInt(promptFor(in, "Seconds"))
                                ),
                                Integer.parseInt(promptFor(in, "Finished in %"))/100,
                                fc.findProjectById(Long.parseLong(promptFor(in, "ProjectID")))
                        ));
                        System.out.println(i);
                        break;
                    case "update issue":
                        printTitle("update issue");
                        // System.out.println("Leave properties empty, if you don't want to change them!!");
                        Long issueId = Long.parseLong(promptFor(in, "IssueID"));

                        cp.updateIssue(issueId, new Issue(
                                State.valueOf(promptFor(in, "State(NEW; OPEN, REJECTED, CLOSED)")),
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
                        printTitle("put issue to project");
                        cp.putIssueToProject(
                                Long.parseLong(promptFor(in, "IssueID")),
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project":
                        printTitle("list issues from project");
                        cp.listIssuesFromProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project by status":
                        printTitle("list issues from project by status");
                        cp.listIssuesFromProjectByStatus(
                                Long.parseLong(promptFor(in, "ProjectID")),
                                State.valueOf(promptFor(in, "State(NEW; OPEN, REJECTED, CLOSED)"))
                        );
                        break;
                    case "needed time per employee of project":
                        printTitle("needed time per employee of project");
                        cp.neededTimePerEmployee(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project grouped by employee":
                        printTitle("list issues from project grouped by employee");
                        cp.listIssuesFromProjectGroupedByEmployee(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list issues from project grouped by employee by status":
                        printTitle("list issues from project grouped by employee by status");
                        cp.listIssuesFromProjectGroupedByEmployeeByStatus(
                                Long.parseLong(promptFor(in, "ProjectID")),
                                State.valueOf(promptFor(in, "State(NEW; OPEN, REJECTED, CLOSED)"))
                        );
                        break;
                    case "list worked time by project":
                        printTitle("list worked time by project");
                        cp.listWorkedTimeByProject(
                                Long.parseLong(promptFor(in, "ProjectID"))
                        );
                        break;
                    case "list phases":
                        printTitle("list phases");
                        cp.listPhases();
                        break;
                    case "create Logbookentry":
                        printTitle("create logbookentry");
                        LogbookEntry e = cp.addLogbookEntry(
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
                        System.out.println(e);
                        break;
                    case "quit":
                        printTitle("quit");

                        break;

                    default:
                        System.err.printf("Invalid Command [%s]%n", userCmd);
                        break;

                } // switch
                System.out.println(help);
                userCmd = promptFor(in, "");

            } // while
        } finally {

        }

    }
}
