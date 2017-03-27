package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
