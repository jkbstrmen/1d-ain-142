package sk.umb.fpv.dain142demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.umb.fpv.dain142demo.domain.model.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
}
