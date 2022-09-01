package com.example.TimeclockAWS.Services;

import com.example.TimeclockAWS.Entities.User;
import com.example.TimeclockAWS.Models.SignUpRequest;
import com.example.TimeclockAWS.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public ResponseEntity<?> signUp(SignUpRequest signUpRequest)
  {
    User user = new User();
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setEmail(signUpRequest.getEmail());
    user.setUsername(signUpRequest.getUsername());
    user.setPassword(signUpRequest.getPassword());
    user.setRole("User");
    System.out.println(user.toString());
    userRepository.save(user);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<List<User>> getUsers()
  {
    return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
  }

  public ResponseEntity<String> getUserProfileImage(String username)
  {
    return new ResponseEntity<>(userRepository.findByEmail(username).get().getImageUrl(),HttpStatus.OK);
  }

}
