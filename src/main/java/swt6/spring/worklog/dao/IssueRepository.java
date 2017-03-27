package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Issue;
import swt6.spring.worklog.domain.LogbookEntry;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}
