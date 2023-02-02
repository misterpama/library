package pama.projects.library.resolver;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pama.projects.library.domain.Author;
import pama.projects.library.domain.AuthorInput;
import pama.projects.library.domain.Book;
import pama.projects.library.repository.AuthorRepository;
import pama.projects.library.repository.BookRepository;

@Controller
public class AuthorController {

  @Autowired AuthorRepository authorRepository;
  @Autowired BookRepository bookRepository;

  @MutationMapping
  public Author newAuthor(@Argument AuthorInput author) {
    return authorRepository.save(Author
        .builder()
        .firstName(author.getFirstName())
        .lastName(author.getLastName())
        .age(author.getAge())
        .nationality(author.getNationality())
        .build());
  }

  @QueryMapping
  public Iterable<Author> authors(DataFetchingEnvironment environment) {
    DataFetchingFieldSelectionSet s = environment.getSelectionSet();
    List<Specification<Author>> specifications = new ArrayList<>();
    if (s.contains("books"))
      return authorRepository.findAll(fetchBooks());
    else
      return authorRepository.findAll();
  }

  @QueryMapping
  public Author author(@Argument Integer id, DataFetchingEnvironment environment) {
    Specification<Author> spec = byId(id);
    DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
    if (selectionSet.contains("books"))
      spec = spec.and(fetchBooks());
    return authorRepository.findOne(spec).orElseThrow(NoSuchElementException::new);
  }

  private Specification<Author> fetchBooks() {
    return (root, query, builder) -> {
      Fetch<Author, Book> f = root.fetch("books", JoinType.LEFT);
      Join<Author, Book> join = (Join<Author, Book>) f;
      return join.getOn();
    };
  }

  private Specification<Author> byId(Integer id) {
    return (root, query, builder) -> builder.equal(root.get("id"), id);
  }
}

