package pama.projects.library.library;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import pama.projects.library.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
public class BookControllerTests {

  @Autowired
  private GraphQlTester tester;

  @Test
  void addBook() {
    String query = "mutation { newBook(book: { name: \"Il codice da Vinci\" isbn: \"3854636\" authorId: 1}) { id } }";
    Book book = tester.document(query)
        .execute()
        .path("data.newBook")
        .entity(Book.class)
        .get();
    Assertions.assertNotNull(book);
    Assertions.assertNotNull(book.getId());
  }

  @Test
  void findAll() {
    String query = "{ books { id name isbn } }";
    List<Book> books = tester.document(query)
        .execute()
        .path("data.books[*]")
        .entityList(Book.class)
        .get();
    Assertions.assertTrue(books.size() > 0);
    Assertions.assertNotNull(books.get(0).getId());
    Assertions.assertNotNull(books.get(0).getName());
  }

  @Test
  void findById() {
    String query = "{ book(id: 1) { id name isbn } }";
    Book book = tester.document(query)
        .execute()
        .path("data.book")
        .entity(Book.class)
        .get();
    Assertions.assertNotNull(book);
    Assertions.assertNotNull(book.getId());
    Assertions.assertNotNull(book.getName());
  }

  @Test
  void findWithFilter() {
    String query = "{ booksWithFilter(filter: { name: { operator: \"contains\" value: \"La\" } }) { id name isbn } }";
    List<Book> books = tester.document(query)
        .execute()
        .path("data.booksWithFilter[*]")
        .entityList(Book.class)
        .get();
    Assertions.assertTrue(books.size() > 0);
    Assertions.assertNotNull(books.get(0).getId());
    Assertions.assertNotNull(books.get(0).getName());
  }
}