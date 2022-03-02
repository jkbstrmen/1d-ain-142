package sk.umb.fpv.dain142demo.service;

import org.springframework.stereotype.Service;
import sk.umb.fpv.dain142demo.domain.model.Borrowing;
import sk.umb.fpv.dain142demo.domain.repository.BorrowingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    private BookService bookService;
    private CustomerService customerService;
    private BorrowingRepository borrowingRepository;

    public BorrowingService(BookService bookService, CustomerService customerService, BorrowingRepository borrowingRepository) {
        this.bookService = bookService;
        this.customerService = customerService;
        this.borrowingRepository = borrowingRepository;
    }

    public Borrowing getBorrowingById(Integer borrowingId) {
        Optional<Borrowing> byId = borrowingRepository.findById(borrowingId);

        // TODO - check if borrowing is present

        return byId.get();
    }

    public Borrowing createBorrowing(Integer bookId, Integer customerId){
        Borrowing borrowing = new Borrowing();

        // TODO - find book and customer using services and set found objects to borrowing

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

}
