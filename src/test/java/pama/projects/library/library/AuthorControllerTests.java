package pama.projects.library.library;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import pama.projects.library.domain.Author;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
public class AuthorControllerTests {

  @Autowired
  private GraphQlTester tester;

  @Test
  void addAuthor() {
    String query = "mutation { newAuthor(author: { firstName: \"John\" lastName: \"Grisham\" age: 65 nationality: \"USA\" }) { id } }";
    Author author = tester.document(query)
        .execute()
        .path("data.newAuthor")
        .entity(Author.class)
        .get();
    Assertions.assertNotNull(author);
    Assertions.assertNotNull(author.getId());
  }

  @Test
  void findAll() {
    String query = "{ authors { id firstName lastName } }";
    List<Author> authors = tester.document(query)
        .execute()
        .path("data.authors[*]")
        .entityList(Author.class)
        .get();
    Assertions.assertTrue(authors.size() > 0);
    Assertions.assertNotNull(authors.get(0).getId());
    Assertions.assertNotNull(authors.get(0).getFirstName());
    Assertions.assertNotNull(authors.get(0).getLastName());
  }

  @Test
  void findById() {
    String query = "{ author(id: 1) { id firstName lastName age nationality } }";
    Author author = tester.document(query)
        .execute()
        .path("data.author")
        .entity(Author.class)
        .get();
    Assertions.assertNotNull(author);
    Assertions.assertNotNull(author.getId());
    Assertions.assertNotNull(author.getFirstName());
    Assertions.assertNotNull(author.getLastName());
    Assertions.assertNotNull(author.getAge());
    Assertions.assertNotNull(author.getNationality());
  }

}