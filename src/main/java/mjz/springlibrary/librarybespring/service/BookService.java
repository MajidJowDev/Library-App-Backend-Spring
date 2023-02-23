package mjz.springlibrary.librarybespring.service;

import mjz.springlibrary.librarybespring.dao.BookRepository;
import mjz.springlibrary.librarybespring.dao.CheckoutRepository;
import mjz.springlibrary.librarybespring.entity.Book;
import mjz.springlibrary.librarybespring.entity.Checkout;
import mjz.springlibrary.librarybespring.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook (String userEmail, Long bookId) throws Exception {

        Optional<Book> book =bookRepository.findById(bookId);

        // we want a user can only check out a book one time
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(), // days to return the book back
                book.get().getId()
                );

        checkoutRepository.save(checkout);

        return  book.get();
    }

    // check to see if the book is checked out by user or not
    public boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if(validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoanCount(String userEmail) {

        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);

        List<Long> bookIdList = new ArrayList<>();

        for(Checkout i: checkoutList) {
            bookIdList.add(i.getBookId());
        }

        //todo
       // List<Book> books = bookRepository.findBooksByBookIds(bookIdList);
        return null;

    }

}
