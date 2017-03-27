package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

}
