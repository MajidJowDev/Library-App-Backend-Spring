package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
