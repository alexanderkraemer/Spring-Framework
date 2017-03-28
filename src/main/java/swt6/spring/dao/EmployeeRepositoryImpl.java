package swt6.spring.dao;

import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.Project;
import swt6.spring.domain.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;


    private Time timeFromMillis(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)));

        return new Time(hours, minutes, seconds);
    }

    @Override
    public List<Issue> getIssuesWithState(Project project, State state) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Issue> cQuery = cb.createQuery(Issue.class);
        Root<Issue> issue = cQuery.from(Issue.class);

        cQuery.select(issue)
                .where(cb.equal(issue.get("zustand"), state));
        if(state == null) {
            cQuery.where(cb.equal(issue.get("project"), project));
        }

        return em.createQuery(cQuery).getResultList();
    }

    @Override
    public Map<Employee, Time> neededTimePerEmployee(Project project) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        Map<Employee, Time> retMap = new HashMap<>();
        project.getEmployees().forEach(e -> {

            CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);
            Root<Issue> issue = criteriaQuery.from(Issue.class);

            criteriaQuery.select(issue)
                    .where(cb.equal(issue.get("project"), project))
                    .where(cb.or(cb.equal(issue.get("zustand"), State.NEW), cb.equal(issue.get("zustand"), State.OPEN)))
                    .where(cb.equal(issue.get("employee"), e));

            List<Issue> list = em.createQuery(criteriaQuery).getResultList();
            System.out.println(list);
            long seconds = 0;
            for(Issue i: list) {
                int sec = i.getEstimatedTime().getSeconds() + (i.getEstimatedTime().getMinutes()*60) + (i.getEstimatedTime().getHours() * 3600);
                seconds += Math.round(sec* (1-i.getFinished()));
            }

            retMap.put(e, this.timeFromMillis(seconds*1000));

        });
        return retMap;
    }

    @Override
    public List<Issue> getIssuesForEmployee(Employee employee, State state) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Issue> allIssuesQuery = cb.createQuery(Issue.class);
        Root<Issue> issue = allIssuesQuery.from(Issue.class);
        ParameterExpression<Employee> e = cb.parameter(Employee.class);
        ParameterExpression<State> s = cb.parameter(State.class);

        Predicate predicate = cb.equal(issue.get("employee"), e);
        if (state != null) {
            predicate = cb.and(predicate, cb.equal(issue.get("zustand"), s));
        }

        CriteriaQuery<Issue> issuesQuery = allIssuesQuery.select(issue).where(predicate);

        TypedQuery<Issue> qry = em.createQuery(issuesQuery);
        qry.setParameter(e, employee);
        if (state != null) {
            qry.setParameter(s, state);
        }
        return qry.getResultList();
    }
}
