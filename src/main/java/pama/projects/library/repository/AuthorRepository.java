package pama.projects.library.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pama.projects.library.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer>,
    JpaSpecificationExecutor<Author> {
}