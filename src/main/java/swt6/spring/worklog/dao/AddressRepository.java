package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Address;

import java.util.Date;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
