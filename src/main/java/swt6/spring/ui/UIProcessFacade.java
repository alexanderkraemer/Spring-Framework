package swt6.spring.ui;

import swt6.spring.domain.Issue;
import swt6.spring.domain.LogbookEntry;
import swt6.spring.domain.Project;
import swt6.spring.domain.State;

public interface UIProcessFacade {
    void listEmployees();

    void createProject(Project p);

    void putEmployeeToProject(Long empl, Long project);

    void removeEmployeeFromProject(Long employee, Long project);

    void listEmployeesFromProject(Long project);

    void createIssue(Issue issue);

    void updateIssue(Long id, Issue issue);

    void putIssueToProject(Long issue, Long project);

    void listIssuesFromProject(Long project);

    void listIssuesFromProjectByStatus(Long project, State state);

    void listIssuesFromProjectGroupedByEmployee(Long project);

    void listIssuesFromProjectGroupedByEmployeeByStatus(Long project, State state);

    void listWorkedTimeByProject(Long project);

    void addLogbookEntry(LogbookEntry entry);

    void listPhases();
}
