package mjz.springlibrary.librarybespring.responsemodels;

import lombok.Data;
import mjz.springlibrary.librarybespring.entity.Book;

@Data
public class ShelfCurrentLoansResponse {

    private Book book;

    private int daysLeft;

    public ShelfCurrentLoansResponse(Book book, int daysLeft) {
        this.book = book;
        this.daysLeft = daysLeft;
    }
}
