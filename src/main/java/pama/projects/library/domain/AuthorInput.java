package pama.projects.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInput {
  private String firstName;
  private String lastName;
  private int age;
  private String nationality;
}
