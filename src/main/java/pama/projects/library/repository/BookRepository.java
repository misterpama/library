package pama.projects.library.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pama.projects.library.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>,
    JpaSpecificationExecutor<Book> {
}