package sk.umb.fpv.dain142demo.domain;

public class Borrowing {

    private Integer bookId;
    private Integer customerId;

    /*
    * GETTERS
    * */
    public Integer getBookId() {
        return bookId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    /*
     * SETTERS
     * */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
