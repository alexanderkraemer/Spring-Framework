package swt6.spring.worklog.logic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sun.jvm.hotspot.utilities.Assert;
import swt6.spring.worklog.dao.*;
import swt6.spring.worklog.domain.*;

@Component("workLog")
@Primary
@Transactional
public class WorkLogImpl implements WorkLogFacade {

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


    @Override
    public Project syncProject(Project project) {
        return projectRepo.saveAndFlush(project);
    }

    @Override
    public Project findProjectById(Long id){
        return projectRepo.findOne(id);
    }

    @Override
    public List<Project> findAllProjects(){
        return projectRepo.findAll();
    }

    @Override
    public void addIssueToProject(Issue issue, Project project) {
        issue.setProject(project);
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepo.saveAndFlush(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeRepo.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public void addEmployeeToProject(Employee employee, Project project) {
        employee.addProject(project);
        project.addEmployee(project, employee);
        employeeRepo.saveAndFlush(employee);
        projectRepo.saveAndFlush(project);
    }

    @Override
    public void removeEmployeeFromProject(Employee employee, Project project) {
        Set<Employee> empls = project.getEmployees();
        empls.remove(employee);

        Set<Project> projects = employee.getProjects();
        projects.remove(project);

        projectRepo.saveAndFlush(project);
        employeeRepo.saveAndFlush(employee);
    }

    @Override
    public Issue syncIssue(Issue issue) throws Exception {
        if(issue.getId() != null && issue.getEmployee() != null) {
            Issue oldIssue = findIssueById(issue.getId());

            if(!oldIssue.getEmployee().getId().equals(issue.getEmployee().getId())) {
                List<LogbookEntry> list = this.findLogbookEntriesByEmployeeAndIssue(oldIssue.getEmployee(), oldIssue);

                if(list.size() > 0) {
                    throw new Exception
                            ("Responsible Employee has already got some logbookentries. So it cannot be changed!");
                }
            }
        }


        if(issue.getEmployee() != null && issue.getEstimatedTime() == null) {
            throw new Exception("Estimated time must be set, when responsible employee is set!");
        }

        return issueRepo.saveAndFlush(issue);
    }

    @Override
    public List<Issue> findAllIssues() {
        return issueRepo.findAll();
    }

    @Override
    public Issue findIssueById(Long id) {
        return issueRepo.findOne(id);
    }

    @Override
    public LogbookEntry addLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepo.save(entry);
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepo.saveAndFlush(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryRepo.findOne(id);
    }

    @Override
    public List<LogbookEntry> findLogbookEntriesByEmployeeAndIssue(Employee employee, Issue issue) {
        List<LogbookEntry> retList = new LinkedList<>();

        logbookEntryRepo.findAll().forEach(entry -> {
            if(entry.getEmployee().getId().equals(employee.getId())){
                if(entry.getIssue().equals(issue)) {
                    retList.add(entry);
                }
            }
        });
        return retList;
    }

    @Override
    public List<LogbookEntry> findAllLogbookEntry() {
        return logbookEntryRepo.findAll();
    }

    @Override
    public Phase addPhase(Phase phase) {
        return phaseRepo.saveAndFlush(phase);
    }

    @Override
    public Phase findPhaseById(Long id) {
        return phaseRepo.findOne(id);
    }

    @Override
    public List<Phase> findAllPhases() {
        return phaseRepo.findAll();
    }
}
