package mjz.springlibrary.librarybespring.controller;

import mjz.springlibrary.librarybespring.entity.Book;
import mjz.springlibrary.librarybespring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000") // enables cross-origin resource sharing only for this specific method. By default, its allows all origins, all headers, and the HTTP methods specified in the
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired // in spring 5 we can remove the Autowired annotation
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception {
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail, bookId);
    }
}
