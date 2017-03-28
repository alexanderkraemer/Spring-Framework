package swt6.spring.dao;

import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.Project;
import swt6.spring.domain.State;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public interface EmployeeRepositoryCustom {
    List<Issue> getIssuesWithState(Project project, State state);

    Map<Employee, Time> neededTimePerEmployee(Project project);

    List<Issue> getIssuesForEmployee(Employee employee, State state);
}
