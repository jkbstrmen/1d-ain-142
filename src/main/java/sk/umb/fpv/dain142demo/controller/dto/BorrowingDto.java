package sk.umb.fpv.dain142demo.controller.dto;


public class BorrowingDto {

    // TODO - implement required field according to REST API

    private Integer id;
    private Integer customerId;
    private String customerName;
    private Integer bookId;
    private String authorName;
    private String title;

    // GETTERS AND SETTERS

    /*
    * GETTERS
    * */
    public Integer getId() {
        return id;
    }

    /*
     * SETTERS
     * */
    public void setId(Integer id) {
        this.id = id;
    }
}
