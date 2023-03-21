package mjz.springlibrary.librarybespring.controller;

import mjz.springlibrary.librarybespring.requestmodels.AddBookRequest;
import mjz.springlibrary.librarybespring.service.AdminService;
import mjz.springlibrary.librarybespring.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }

        adminService.increaseBookQuantity(bookId);
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }

        adminService.postBook(addBookRequest);
    }
}
