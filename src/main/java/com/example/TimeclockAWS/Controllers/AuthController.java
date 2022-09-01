package com.example.TimeclockAWS.Controllers;

import ch.qos.logback.core.rolling.helper.FileNamePattern;
import com.example.TimeclockAWS.Entities.User;
import com.example.TimeclockAWS.JWT.JwtResponse;
import com.example.TimeclockAWS.JWT.JwtUtils;
import com.example.TimeclockAWS.Models.LoginRequest;
import com.example.TimeclockAWS.Models.SignUpRequest;
import com.example.TimeclockAWS.Repositories.UserRepository;
import com.example.TimeclockAWS.S3Services.S3Utils;
import com.example.TimeclockAWS.Services.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtils jwtUtils;


  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
      .map(item -> item.getAuthority())
      .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt,
      Long.parseLong(userDetails.getId()),
      userDetails.getUsername(),
      userDetails.getEmail(),
      roles));
  }

 /* @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
   // System.out.println(signUpRequest.getImageFile().getOriginalFilename());

  /*if (userRepository.findByEmail(signUpRequest.getUsername()).isPresent()) {
      return ResponseEntity
        .badRequest()
        .body("Error: Username is already taken!");
    }
    if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
      return ResponseEntity
        .badRequest()
        .body("Error: Email is already in use!");
    }
    if(!uploadFile(signUpRequest.getEmail(), multipartFile))
    {
      return ResponseEntity
        .badRequest()
        .body("Error: Profile image error!");
    }

    User newUser = new User();
    newUser.setUsername(signUpRequest.getUsername());
    newUser.setEmail(signUpRequest.getEmail());
    newUser.setFirstName(signUpRequest.getFirstName());
    newUser.setLastName(signUpRequest.getLastName());
    newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    newUser.setRole(signUpRequest.getRole());
    newUser.setImageUrl(S3Utils.getURL("avatar.jpg",signUpRequest.getEmail()));
    userRepository.save(newUser);
    return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
  }*/

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestParam(value="file") MultipartFile multipartFile,
                                        @ModelAttribute(value="info") String userinfo) {

    String fileName = multipartFile.getOriginalFilename();
    ObjectMapper mapper = new ObjectMapper();
    try {
      SignUpRequest signUpRequest = mapper.readValue(userinfo,SignUpRequest.class);
      System.out.println("email: " + signUpRequest.getEmail() + " firstname : "+ signUpRequest.getFirstName() );
      System.out.println("lastname: " + signUpRequest.getLastName() + " password : "+ signUpRequest.getPassword() );
      System.out.println("role: " + signUpRequest.getRole() + " username : "+ signUpRequest.getUsername() );
      System.out.println("FileName: " + fileName);


      if (userRepository.findByEmail(signUpRequest.getUsername()).isPresent()) {
        return ResponseEntity
          .badRequest()
          .body("Error: Username is already taken!");
      }
      if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
        return ResponseEntity
          .badRequest()
          .body("Error: Email is already in use!");
      }
      if(!uploadFile(signUpRequest.getEmail(), multipartFile))
      {
        return ResponseEntity
          .badRequest()
          .body("Error: Profile image error!");
      }

      User newUser = new User();
      newUser.setUsername(signUpRequest.getUsername());
      newUser.setEmail(signUpRequest.getEmail());
      newUser.setFirstName(signUpRequest.getFirstName());
      newUser.setLastName(signUpRequest.getLastName());
      newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
      newUser.setRole(signUpRequest.getRole());
      newUser.setImageUrl(S3Utils.getURL(multipartFile.getOriginalFilename(),signUpRequest.getEmail()));
      userRepository.save(newUser);


      System.out.println(newUser.toString());

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
  }
/*
  @PostMapping("/imageUpload")
  public boolean uploadFile(@RequestParam(value="file") MultipartFile multipartFile,@ModelAttribute(value="info") String userinfo)
  {
    String fileName = multipartFile.getOriginalFilename();
    ObjectMapper mapper = new ObjectMapper();
    try {
      SignUpRequest signUpRequest = mapper.readValue(userinfo,SignUpRequest.class);
      System.out.println("email: " + signUpRequest.getEmail() + " firstname : "+ signUpRequest.getFirstName() );
      System.out.println("lastname: " + signUpRequest.getLastName() + " password : "+ signUpRequest.getPassword() );
      System.out.println("role: " + signUpRequest.getRole() + " username : "+ signUpRequest.getUsername() );
      System.out.println("FileName: " + fileName);

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }





   /* try {
      S3Utils.uploadFile(email+"/avatar.jpg",multipartFile.getInputStream());
      System.out.println("Avatar file uploaded successfully!");
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }*/

  public boolean uploadFile(String email, MultipartFile multipartFile)
  {
    String file = multipartFile.getOriginalFilename();
    System.out.println("file name: " + file);

   try {
      S3Utils.uploadFile(email+"/"+multipartFile.getOriginalFilename(),multipartFile.getInputStream());
      System.out.println("Avatar file uploaded successfully!");
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    System.out.println(email+"/"+multipartFile.getOriginalFilename());
    return true;
  }


}
