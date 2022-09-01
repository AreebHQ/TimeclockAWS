package com.example.TimeclockAWS.Services;


import com.example.TimeclockAWS.Entities.Shift;
import com.example.TimeclockAWS.Entities.User;
import com.example.TimeclockAWS.Repositories.ShiftRepository;
import com.example.TimeclockAWS.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ShiftService {

  @Autowired
  ShiftRepository shiftRepository;
  @Autowired
  UserRepository userRepository;

  public ResponseEntity<?> currentShift(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.isPresent())
    {
      System.out.println("Current Shift Not Found: " + checkShift);
      return new ResponseEntity<>("Current Shift Not Found",HttpStatus.NOT_ACCEPTABLE);
    }
    return new ResponseEntity<>(checkShift.get(),HttpStatus.OK);
  }

  public ResponseEntity<List<Shift>> getShifts(String user) {
    Optional<User> getUser = userRepository.findByEmail(user);
    return new ResponseEntity<>(shiftRepository.findByUserOrderByShiftDateDesc(getUser.get()),HttpStatus.OK);
  }

  public ResponseEntity<?> startShift(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(!checkShift.isEmpty())
    {
      System.out.println("Shift Present: " + checkShift);
      return new ResponseEntity<>("Shift Already Exist",HttpStatus.BAD_REQUEST);
    }
    //if shift doesn't exist
    Shift shift = new Shift();
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    shift.setShiftDate(LocalDate.now().toString());
    shift.setStartShift(timeStamp);
    shift.setUser(userRepository.findByEmail(user).get());
    shift.setShiftActive(true);
    shift.setBreakActive(false);
    shift.setLunchActive(false);
    System.out.println(shift.toString());
    shiftRepository.save(shift);
    return new ResponseEntity<>("Shift Started Successfully",HttpStatus.OK);
  }

  public ResponseEntity<?> endShift(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.get().isShiftActive() || checkShift.get().isBreakActive() || checkShift.get().isLunchActive())
    {
      System.out.println("Shift doesn't exist or not-active or lunch-break is active: " + checkShift);
      return new ResponseEntity<>("Shift doesn't exist or not-active or lunch-break is active",HttpStatus.NOT_ACCEPTABLE);
    }
   // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    checkShift.get().setEndShift(timeStamp);
    checkShift.get().setShiftActive(false);
    shiftRepository.save(checkShift.get());
    return new ResponseEntity<>(checkShift.get(),HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<?> startBreak(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.get().isShiftActive() || checkShift.get().isBreakActive() || checkShift.get().isLunchActive())
    {
      System.out.println("Shift doesn't exist or not-active or lunch-break is active: " + checkShift);
      return new ResponseEntity<>("Shift doesn't exist or not-active or lunch-break is active",HttpStatus.NOT_ACCEPTABLE);
    }
   // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    checkShift.get().setStartBreak(timeStamp);
    checkShift.get().setBreakActive(true);
    shiftRepository.save(checkShift.get());
    return new ResponseEntity<>(checkShift.get(),HttpStatus.OK);
  }

  public ResponseEntity<?> endBreak(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.get().isShiftActive() || !checkShift.get().isBreakActive() || checkShift.get().isLunchActive())
    {
      System.out.println("Shift doesn't exist or not-active or lunch-break is active: " + checkShift);
      return new ResponseEntity<>("Shift doesn't exist or not-active or lunch-break is active",HttpStatus.NOT_ACCEPTABLE);
    }
   // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    checkShift.get().setEndBreak(timeStamp);
    checkShift.get().setBreakActive(false);
    return new ResponseEntity<>(shiftRepository.save(checkShift.get()),HttpStatus.OK);
  }

  public ResponseEntity<?> startLunch(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.get().isShiftActive() || checkShift.get().isBreakActive() || checkShift.get().isLunchActive())
    {
      System.out.println("Shift doesn't exist or not-active or lunch-break is active: " + checkShift);
      return new ResponseEntity<>("Shift doesn't exist or not-active or lunch-break is active",HttpStatus.NOT_ACCEPTABLE);
    }
   // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    checkShift.get().setStartLunch(timeStamp);
    checkShift.get().setLunchActive(true);
    shiftRepository.save(checkShift.get());
    return new ResponseEntity<>(checkShift.get(),HttpStatus.OK);
  }

  public ResponseEntity<?> endLunch(String user)
  {
    Optional<Shift> checkShift = Optional.ofNullable(shiftRepository.findByShiftDateAndUser(LocalDate.now().toString(),userRepository.findByEmail(user).get()));
    // if shift exist
    if(checkShift.isEmpty() || !checkShift.get().isShiftActive() || checkShift.get().isBreakActive() || !checkShift.get().isLunchActive())
    {
      System.out.println("Shift doesn't exist or not-active or lunch-break is active: " + checkShift);
      return new ResponseEntity<>("Shift doesn't exist or not-active or lunch-break is active",HttpStatus.NOT_ACCEPTABLE);
    }
   // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new java.util.Date());
    checkShift.get().setEndLunch(timeStamp);
    checkShift.get().setLunchActive(false);
    return new ResponseEntity<>(shiftRepository.save(checkShift.get()),HttpStatus.OK);
  }


}
