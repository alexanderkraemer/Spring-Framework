package swt6.spring.domain;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
public class Issue implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private State zustand;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @Nullable
    private Time estimatedTime;

    private double finished;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    public Issue() {
    }

    public Issue(State zustand, Priority priority, Time estimatedTime, double finished, Project project) {
        this.zustand = zustand;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.finished = finished;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getZustand() {
        return zustand;
    }

    public void setZustand(State zustand) {
        this.zustand = zustand;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Time getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Time estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getFinished() {
        return finished;
    }

    public void setFinished(double finished) {
        if(finished >= 0.0 && finished <= 1.0) {
            this.finished = finished;
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String toString(){
        return "Issue " + this.getId() + ": [" + getZustand() + "] Finished Status: " + getFinished() * 100 + "%; Estimated Time: " + getEstimatedTime();
    }
}
