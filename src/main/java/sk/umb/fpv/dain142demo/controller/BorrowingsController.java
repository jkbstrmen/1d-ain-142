package sk.umb.fpv.dain142demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sk.umb.fpv.dain142demo.controller.dto.BorrowingDto;
import sk.umb.fpv.dain142demo.domain.model.Borrowing;
import sk.umb.fpv.dain142demo.service.BorrowingService;

import java.util.List;

@RestController
public class BorrowingsController {

    private BorrowingService borrowingService;

    public BorrowingsController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping("/api/borrowings/{borrowingId}")
    public BorrowingDto getBorrowing(@PathVariable Integer borrowingId) {
        Borrowing borrowing = borrowingService.getBorrowingById(borrowingId);

        // TODO - map required properties from borrowing to borrowingDto
        BorrowingDto borrowingDto = new BorrowingDto();

        return borrowingDto;
    }

}
