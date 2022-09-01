package com.example.TimeclockAWS.Repositories;

import com.example.TimeclockAWS.Entities.Shift;
import com.example.TimeclockAWS.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

  Shift findByShiftDate(String date);
  Shift findByShiftDateAndUser(String date, User user);
  List<Shift> findByUserOrderByShiftDateDesc(User user);
}
