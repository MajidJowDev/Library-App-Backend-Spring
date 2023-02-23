package mjz.springlibrary.librarybespring.dao;

import mjz.springlibrary.librarybespring.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// new API interface
public interface BookRepository extends JpaRepository<Book, Long> {

    //Page is a sublist of a list of objects. It allows gain information about the position of it in the containing entire lis
    // this method returns a pageable List of books, and will add "search": {"href": "http://localhost:8080/api/books/search"} as a new endpoint to our API _links (automatically by Spring Data REST)
    //"http://localhost:8080/api/books/search/findByTitleContaining{?title,page,size,sort}
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query("select o from Book o where o.id in :book_ids") // since our method is complex, we need to define a query to tell spring how to get data
    List<Book> findBooksByBookIds(@Param("book_ids") List<Long> bookId);

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