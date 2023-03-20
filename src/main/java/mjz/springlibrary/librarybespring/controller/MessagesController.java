package mjz.springlibrary.librarybespring.controller;

import mjz.springlibrary.librarybespring.entity.Message;
import mjz.springlibrary.librarybespring.requestmodels.AdminQuestionRequest;
import mjz.springlibrary.librarybespring.service.MessagesService;
import mjz.springlibrary.librarybespring.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token,
                            @RequestBody Message messageRequest) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messagesService.postMessage(messageRequest, userEmail);

    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        // check if the user is "admin" check the "5. Create Admin User" for creating an admin user and adding the userType to "claim" / payload in okta settings
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }

        messagesService.putMessage(adminQuestionRequest, userEmail);

    }
}
