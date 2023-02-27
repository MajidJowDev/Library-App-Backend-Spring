package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
