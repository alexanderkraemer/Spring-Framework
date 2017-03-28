package swt6.spring.ui;

import swt6.spring.domain.*;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public interface UIProcessFacade {
    void listEmployees();

    Project createProject(Project p);

    void putEmployeeToProject(Long empl, Long project);

    void removeEmployeeFromProject(Long employee, Long project);

    void listEmployeesFromProject(Long project);

    Issue createIssue(Issue issue);

    void updateIssue(Long id, Issue issue);

    void putIssueToProject(Long issue, Long project);

    void neededTimePerEmployee(Long project);

    void getIssuesForEmployee(Long employee, State state);

    void listIssuesFromProject(Long project);

    void listIssuesFromProjectByStatus(Long project, State state);

    void listIssuesFromProjectGroupedByEmployee(Long project);

    void listIssuesFromProjectGroupedByEmployeeByStatus(Long project, State state);

    void listWorkedTimeByProject(Long project);

    LogbookEntry addLogbookEntry(LogbookEntry entry);

    void listPhases();
}
