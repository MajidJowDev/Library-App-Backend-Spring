package mjz.springlibrary.librarybespring.config;

import mjz.springlibrary.librarybespring.entity.Book;
import mjz.springlibrary.librarybespring.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// We have to implement Spring Data REST 'RepositoryRestConfigurer' to be able to configure our API settings (e.g Enable/Disabling some actions)
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000"; // this url allows us to properly make requests to the FRONT END (REACT) // for CORS mapping

    //by Overriding this method we can add additional configuration to RepositoryREST.
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //Cross-Origin Resource Sharing (CORS) is an HTTP-header based mechanism that allows a server to indicate any origins (domain, scheme, ...)
        // cross-origin request handling for the specified path pattern.

        // creating a list of request methods that would not be accepted
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PATCH,
                HttpMethod.PUT};

        config.exposeIdsFor(Book.class); // with this method we can Set the list of domain types for which we will expose the ID value as a normal property
        //config.exposeIdsFor(Book.class, Author.class, Review.class); we can add more classes if needed, loke so
        config.exposeIdsFor(Review.class);

        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);

        //*** Configure CORS mapping ***
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins); //Set the origins for which cross-origin requests are allowed from a browser

    }

    private void disableHttpMethods(Class theClass,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions) {

        // (ExposureConfiguration) Configuration type to register filters customizing the HTTP methods supported
        config.getExposureConfiguration()
                .forDomainType(theClass)// applies this rule for the specific class (in here Book)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions)) // disabling the unsupported methods for SINGLE item
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions)); // disabling the unsupported methods for COLLECTION of items
    }

}
