package mjz.springlibrary.librarybespring.controller;

import mjz.springlibrary.librarybespring.entity.Book;
import mjz.springlibrary.librarybespring.service.BookService;
import mjz.springlibrary.librarybespring.utils.ExtractJWT;
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

    //we pass the JWT to backend endpoint and backend will verify the JWT and extract the information we need for the endpoint from the jwt
    //we need to extract information from access token (JWT) 'sub' so we can access to user info and authenticate the user to access the api
    //You can check the access token payload information from jwt.io site
    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");// extract user email from JWT token "sub" //"testuser@email.com";
        return bookService.currentLoanCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token,
                             @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }
}
