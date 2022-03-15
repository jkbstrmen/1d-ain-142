package sk.umb.fpv.dain142demo.domain.model;

import javax.persistence.*;

@Entity
public class Borrowing {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Join column name defines the name of column where we want to store primary key of rows of referenced table.
     * @ManyToOne in this example means, that many borrowings can reference to one book.
     * For difference between fetch type LAZY vs EAGER see readme.
     */
    @JoinColumn(name = "book_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;


    // TODO - add Customer field

    // TODO - implement getters and setters

}
