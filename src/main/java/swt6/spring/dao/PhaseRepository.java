package swt6.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

}
