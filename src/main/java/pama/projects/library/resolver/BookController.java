package pama.projects.library.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pama.projects.library.domain.Author;
import pama.projects.library.domain.Book;
import pama.projects.library.domain.BookInput;
import pama.projects.library.filter.FilterField;
import pama.projects.library.filter.BookFilter;
import pama.projects.library.repository.AuthorRepository;
import pama.projects.library.repository.BookRepository;

@Controller
public class BookController {

  @Autowired
  AuthorRepository authorRepository;
  @Autowired
  BookRepository bookRepository;

  @QueryMapping
  public Iterable<Book> books() {
    return bookRepository.findAll();
  }

  @QueryMapping
  public Book book(@Argument Integer id) {
    return bookRepository.findById(id).orElseThrow();
  }

  @MutationMapping
  public Book newBook(@Argument BookInput book) {
    Author author = authorRepository.findById(book.getAuthorId()).get();
    return bookRepository.save(Book
        .builder()
        .name(book.getName())
        .isbn(book.getIsbn())
        .author(author)
        .build());
  }

  @QueryMapping
  public Iterable<Book> booksWithFilter(@Argument BookFilter filter) {
    Specification<Book> spec = null;
    if (filter.getName() != null)
      spec = byName(filter.getName());
    if (filter.getIsbn() != null)
      spec = byIsbn(filter.getIsbn());
    if (spec != null)
      return bookRepository.findAll(spec);
    else
      return bookRepository.findAll();
  }

  private Specification<Book> byName(FilterField filterField) {
    return (root, query, builder) -> filterField.generateCriteria(builder, root.get("name"));
  }

  private Specification<Book> byIsbn(FilterField filterField) {
    return (root, query, builder) -> filterField.generateCriteria(builder, root.get("isbn"));
  }
}
