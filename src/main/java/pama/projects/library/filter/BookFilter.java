package pama.projects.library.filter;

import lombok.Data;

@Data
public class BookFilter {
  private FilterField name;
  private FilterField isbn;
}
