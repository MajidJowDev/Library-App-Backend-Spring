package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    List<Checkout> findBooksByUserEmail(String userEmail); // get a list of books that the user have checked out

    @Modifying // we are going to delete (modify) the record
    @Query("delete from Checkout c where c.bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
