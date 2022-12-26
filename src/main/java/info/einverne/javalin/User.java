package info.einverne.javalin;

import java.io.Serializable;

/**
 * @since 2022-09-26
 */
public class User implements Serializable {

  private String name;
  private int age;

  public User(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
