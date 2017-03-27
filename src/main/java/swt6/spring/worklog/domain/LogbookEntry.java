package swt6.spring.worklog.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class LogbookEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String activity;

    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Phase phase;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Issue issue;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, Date startTime, Date endTime, Employee emp, Phase phase, Issue issue) {
        this.employee = emp;
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phase = phase;
        this.issue = issue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "LogbookEntry [id=" + id + ", activity=" + activity + ", startTime=" + startTime + ", endTime=" + endTime
                + "]";
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
