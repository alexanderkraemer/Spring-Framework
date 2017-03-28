package swt6.spring.logic;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import swt6.spring.domain.*;

public interface WorkLogFacade {
    Project syncProject(Project project);
    Project findProjectById(Long id);
    List<Project> findAllProjects();
    void addIssueToProject(Issue issue, Project project);

    Phase addPhase(Phase phase);
    Phase findPhaseById(Long id);
    List<Phase> findAllPhases();
    
    Employee syncEmployee(Employee employee);
    Employee findEmployeeById(Long id);
    List<Employee> findAllEmployees();
    void addEmployeeToProject(Employee employee, Project project);
    void removeEmployeeFromProject(Employee employee, Project project);

    Issue syncIssue(Issue issue) throws Exception;
    List<Issue> findAllIssues();
    Issue findIssueById(Long id);

    List<Issue> getIssuesWithState(Project project, State state);
    Map<Employee, Time> neededTimePerEmployee(Project project);
    List<Issue> getIssuesForEmployee(Employee employee, State state);

    LogbookEntry addLogbookEntry(LogbookEntry entry);
    List<LogbookEntry> findLogbookEntriesByEmployeeAndIssue(Employee employee, Issue issue);
    LogbookEntry syncLogbookEntry(LogbookEntry entry);
    LogbookEntry findLogbookEntryById(Long id);
    List<LogbookEntry> findAllLogbookEntry();
}