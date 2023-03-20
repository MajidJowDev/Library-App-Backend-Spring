package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

    // admin will use this endpoint to see the messages (find it by all users)
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);
}
