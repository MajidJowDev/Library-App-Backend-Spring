package mjz.springlibrary.librarybespring.requestmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;

    private Long bookId;

    private Optional<String> reviewDescription; // set to optional because this property is not required when a user leaves a review for a book
}
