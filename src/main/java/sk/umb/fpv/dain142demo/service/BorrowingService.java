package sk.umb.fpv.dain142demo.service;

import org.springframework.stereotype.Service;
import sk.umb.fpv.dain142demo.controller.dto.BorrowingDto;
import sk.umb.fpv.dain142demo.domain.Book;
import sk.umb.fpv.dain142demo.domain.Borrowing;
import sk.umb.fpv.dain142demo.domain.Customer;

@Service
public class BorrowingService {

    private BookService bookService;
    private CustomerService customerService;

    public BorrowingService(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public BorrowingDto getBorrowingById(Integer borrowingId) {

        // TODO - find borrowing by borrowingId
        Borrowing borrowing = new Borrowing();

        // get customer by id from borrowing
        Customer customer = customerService.getById(borrowing.getCustomerId());

        // get book by id from borrowing
        Book book = bookService.getById(borrowing.getBookId());

        // TODO - map required properties from book and customer to borrowingDto
        BorrowingDto borrowingDto = new BorrowingDto();

        return borrowingDto;
    }

}
