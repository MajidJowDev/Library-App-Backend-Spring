package mjz.springlibrary.librarybespring.service;

import mjz.springlibrary.librarybespring.dao.BookRepository;
import mjz.springlibrary.librarybespring.dao.ReviewRepository;
import mjz.springlibrary.librarybespring.entity.Review;
import mjz.springlibrary.librarybespring.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired // we can remove this annotation in Spring 5
    public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {

        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

        if(validateReview != null) {
            throw new Exception("Review already created!");
        }

        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if(reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }

        review.setDate(Date.valueOf(LocalDate.now()));

        reviewRepository.save(review);

    }
}
