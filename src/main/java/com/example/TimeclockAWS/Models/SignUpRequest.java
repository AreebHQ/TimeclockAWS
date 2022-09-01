package com.example.TimeclockAWS.Models;

import org.springframework.web.multipart.MultipartFile;

public class SignUpRequest {

  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
  private String role;
 /*  private MultipartFile imageFile;

  public MultipartFile getImageFile() {
    return imageFile;
  }*/

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "SignUpRequest{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", email='" + email + '\'' +
      ", role='" + role + '\'' +
      '}';
  }
}
