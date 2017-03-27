package swt6.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
