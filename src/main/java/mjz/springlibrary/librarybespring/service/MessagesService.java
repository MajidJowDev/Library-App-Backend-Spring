package mjz.springlibrary.librarybespring.service;

import mjz.springlibrary.librarybespring.dao.MessageRepository;
import mjz.springlibrary.librarybespring.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessagesService {

    private final MessageRepository messageRepository;

    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

}