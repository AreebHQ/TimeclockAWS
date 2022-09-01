package com.example.TimeclockAWS.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @PreAuthorize("hasAuthority('Admin')")
  public ResponseEntity<?> getAdmin()
  {
    return new ResponseEntity<>("Successful from Admin controller!", HttpStatus.OK);
  }
}
