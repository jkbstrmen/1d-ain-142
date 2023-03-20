package sk.umb.fpv.dain142demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.umb.fpv.dain142demo.domain.model.Customer;
import sk.umb.fpv.dain142demo.domain.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer.setId(null);
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return ResponseEntity.ok(customer.get());
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomers(@RequestParam String lastName, @RequestParam Integer page,
            @RequestParam Integer size) {
        System.out.println("lastName: " + lastName);
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> all = null;
        if (lastName == null) {
            all = customerRepository.findAll(pageable);
        } else {
            all = customerRepository.findByLastName(lastName, pageable);
        }
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer() {

    }

}
