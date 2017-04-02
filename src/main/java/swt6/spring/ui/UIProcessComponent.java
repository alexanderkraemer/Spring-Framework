package swt6.spring.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;
import swt6.spring.domain.*;
import swt6.spring.logic.WorkLogFacade;

import javax.persistence.EntityManagerFactory;
import java.sql.Time;
import java.util.*;

@Component
@Primary
public class UIProcessComponent implements UIProcessFacade {
    @Autowired
    private WorkLogFacade fc;

    @Override
    public void listEmployees() {
        fc.findAllEmployees().forEach(System.out::println);
    }

    @Override
    public Project createProject(Project p) {
        fc.syncProject(p);
        return p;
    }

    @Override
    public void putEmployeeToProject(Long employee, Long project) {
        fc.addEmployeeToProject(fc.findEmployeeById(employee), fc.findProjectById(project));
    }

    @Override
    public void removeEmployeeFromProject(Long employee, Long project) {
        fc.removeEmployeeFromProject(fc.findEmployeeById(employee), fc.findProjectById(project));
    }

    @Override
    public void listEmployeesFromProject(Long project) {
        fc.findProjectById(project).getEmployees().forEach(System.out::println);
    }

    @Override
    public Issue createIssue(Issue issue) {
        try {
            fc.syncIssue(issue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return issue;
    }

    @Override
    public void updateIssue(Long id, Issue issue) {
        try {
            Issue old = fc.findIssueById(id);
            old.setZustand(issue.getZustand());
            old.setPriority(issue.getPriority());
            old.setEstimatedTime(issue.getEstimatedTime());
            old.setFinished(issue.getFinished());
            old.setProject(issue.getProject());
            old.setEmployee(issue.getEmployee());

            fc.syncIssue(old);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void putIssueToProject(Long issue, Long project) {
        fc.addIssueToProject(fc.findIssueById(issue), fc.findProjectById(project));
    }

    @Override
    public void listIssuesFromProject(Long project) {
        Set<Issue> issueSet = new HashSet<>();

        fc.findAllIssues().forEach(i -> {
            if(i.getProject().getId().equals(project)) {
                issueSet.add(i);
            }

        });

        issueSet.forEach(System.out::println);
    }
/*
    @Override
    public void listIssuesFromProjectByStatus(Long project, State state) {
        Set<Issue> issueSet = new HashSet<>();

        fc.findAllIssues().forEach(i -> {
            if(i.getProject().getId().equals(project) && i.getZustand().equals(state)) {
                issueSet.add(i);
            }
        });

        issueSet.forEach(System.out::println);
    }
*/

    @Override
    public void getIssuesForEmployee(Long employee, State state) {
        fc.getIssuesForEmployee(fc.findEmployeeById(employee), state).forEach(System.out::println);
    }

    @Override
    public void neededTimePerEmployee(Long project) {
        Map<Employee, Time> map = fc.neededTimePerEmployee(fc.findProjectById(project));
        map.forEach((k,v) -> System.out.println(k + " - " + v));
    }

    @Override
    public void listIssuesFromProjectByStatus(Long project, State state) {
        fc.getIssuesWithState(fc.findProjectById(project), state).forEach(System.out::println);
    }

    @Override
    public void listIssuesFromProjectGroupedByEmployee(Long project) {
        Map<Employee, List<Issue>> map = new HashMap<>();

        fc.findAllIssues().forEach(i -> {

            List<Issue> issues = map.computeIfAbsent(i.getEmployee(), k -> new ArrayList<>());
            issues.add(i);
        });

        this.printMap(map);
    }

    @Override
    public void listIssuesFromProjectGroupedByEmployeeByStatus(Long project, State state) {
        Map<Employee, List<Issue>> map = new HashMap<>();

        fc.findAllIssues().forEach(i -> {

            List<Issue> issues = map.computeIfAbsent(i.getEmployee(), k -> new ArrayList<>());
            if(i.getZustand().equals(state)) {
                issues.add(i);
            }
        });

        this.printMap(map);
    }

    @Override
    public void listWorkedTimeByProject(Long project) {
        Map<Employee, List<Issue>> map = new HashMap<>();

        fc.findAllIssues().forEach(i -> {

            List<Issue> issues = map.computeIfAbsent(i.getEmployee(), k -> new ArrayList<>());
            issues.add(i);
        });

        map.forEach((k,v) -> {
            System.out.println("===================================================");
            System.out.println(k);
            System.out.println("===================================================");
            Long usedTime = 0L;
            Long neededTime = 0L;
            Long estimatedTime = 0L;
            for(Issue i: v) {
                Long seconds = i.getEstimatedTime().getTime();
                usedTime += Math.round(seconds * i.getFinished());
                neededTime += Math.round(seconds * (1-i.getFinished()));
                estimatedTime += Math.round(seconds);
            }
            Float percent = ((float)usedTime)/estimatedTime;
            System.out.println("Estimated time: " + this.timeFromMillis(estimatedTime));
            System.out.println("Finished in Percentage: " + percent*100  + "%");
            System.out.println("Already used time: " + this.timeFromMillis(usedTime));
            System.out.println("Still needed time: " + this.timeFromMillis(neededTime));
        });
    }

    private void printMap(Map<Employee, List<Issue>> map) {
        map.forEach((k,v) -> {
            System.out.println("===================================================");
            System.out.println(k);
            System.out.println("===================================================");
            v.forEach(System.out::println);
        });
    }

    @Override
    public LogbookEntry addLogbookEntry(LogbookEntry entry) {
        fc.addLogbookEntry(entry);
        return entry;
    }

    @Override
    public void listPhases() {
        fc.findAllPhases().forEach(System.out::println);
    }

    private Time timeFromMillis(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)));

        return new Time(hours, minutes, seconds);

    }
}
