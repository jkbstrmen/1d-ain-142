package sk.umb.fpv.dain142demo.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    // TODO - add other fields, implement getters and setters

}
