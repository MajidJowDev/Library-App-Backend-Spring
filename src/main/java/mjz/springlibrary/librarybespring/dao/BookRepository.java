package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// new API interface
public interface BookRepository extends JpaRepository<Book, Long> {
}



/*

*****This is an extremely fast way to implement a simple API****

By using spring data jpa and spring.data.rest we can access to API json response automatically without needing
to implement any controllers, etc (JpaRepository handles all of the APIs for us)

so if we call the API base url we set on application properties, we receive all the possible API sub-addresses
that is available in our application

the Rest API base address set in app porperties is:
spring.data.rest.base-path=/api

so if we call the "http://localhost:8080/api", we'll get the following response

{
  "_links" : {
    "books" : {
      "href" : "http://localhost:8080/api/books{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile"
    }
  }
}

and as it's clear, now that we added the BookRepository, we get the books API Endpoint url, so if we call the books url,
 we get a list of all available books that are stored in MySQL database table 'book', in JSON format (with pagination feature and all)


 */