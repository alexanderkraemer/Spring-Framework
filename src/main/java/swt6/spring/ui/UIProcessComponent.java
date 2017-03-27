package swt6.spring.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
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
    public void createProject(Project p) {
        fc.syncProject(p);
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
    public void createIssue(Issue issue) {
        try {
            fc.syncIssue(issue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            Double percent = 0.0;
            for(Issue i: v) {
                Long seconds = (long)i.getEstimatedTime().getSeconds() + i.getEstimatedTime().getMinutes()*60 + i.getEstimatedTime().getHours() * 3600;
                usedTime += Math.round(seconds * i.getFinished());
                neededTime += Math.round(seconds * (1-i.getFinished()));
                estimatedTime += Math.round(seconds);
                percent += i.getFinished();
            }
            percent = percent/v.size();
            System.out.println("Estimated time: " + this.timeFromMillis(estimatedTime*1000));
            System.out.println("Finished in Percentage: " + percent*100 + "%");
            System.out.println("Already used time: " + this.timeFromMillis(usedTime*1000));
            System.out.println("Still needed time: " + this.timeFromMillis(neededTime*1000));
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
    public void addLogbookEntry(LogbookEntry entry) {
        fc.addLogbookEntry(entry);
    }

    @Override
    public void listPhases() {
        fc.findAllLogbookEntry().forEach(System.out::println);
    }

    private Time timeFromMillis(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)));

        return new Time(hours, minutes, seconds);

    }
}
