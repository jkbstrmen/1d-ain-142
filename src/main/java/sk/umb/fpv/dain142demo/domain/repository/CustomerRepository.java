package sk.umb.fpv.dain142demo.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.umb.fpv.dain142demo.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByLastNameContaining(String lastName);

    @Query("SELECT c FROM Customer c WHERE c.lastName LIKE CONCAT('%',:lastName,'%')")
    Page<Customer> findByLastName(String lastName, Pageable pageable);

}
