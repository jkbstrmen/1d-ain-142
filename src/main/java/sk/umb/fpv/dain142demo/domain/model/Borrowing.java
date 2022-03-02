package sk.umb.fpv.dain142demo.domain.model;

import javax.persistence.*;

@Entity
public class Borrowing {

    @Id
    @GeneratedValue
    private Integer id;

    // @JoinColumn and @ManyToOne will be explained next lesson

    @JoinColumn
    @ManyToOne
    private Book book;

    @JoinColumn
    @ManyToOne
    private Customer customer;


    // TODO - implement getters and setters

}
