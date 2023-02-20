package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail); // get a list of books that the user have checked out
}
