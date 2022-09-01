package com.example.TimeclockAWS.Entities;

import javax.persistence.*;

@Entity
@Table(name="shift")
public class Shift {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "shift_date")
  private String shiftDate;
  @Column(name = "start_shift")
  private String startShift;
  @Column(name = "end_shift")
  private String endShift;
  @Column(name = "start_break")
  private String startBreak;
  @Column(name = "end_break")
  private String endBreak;
  @Column(name = "start_lunch")
  private String startLunch;
  @Column(name = "end_lunch")
  private String endLunch;
  @Column(name = "shift_active")
  private boolean shiftActive;
  @Column(name = "break_active")
  private boolean breakActive;
  @Column(name = "lunch_active")
  private boolean lunchActive;
  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getShiftDate() {
    return shiftDate;
  }

  public void setShiftDate(String shiftDate) {
    this.shiftDate = shiftDate;
  }

  public String getStartShift() {
    return startShift;
  }

  public void setStartShift(String startShift) {
    this.startShift = startShift;
  }

  public String getEndShift() {
    return endShift;
  }

  public void setEndShift(String endShift) {
    this.endShift = endShift;
  }

  public String getStartBreak() {
    return startBreak;
  }

  public void setStartBreak(String startBreak) {
    this.startBreak = startBreak;
  }

  public String getEndBreak() {
    return endBreak;
  }

  public void setEndBreak(String endBreak) {
    this.endBreak = endBreak;
  }

  public String getStartLunch() {
    return startLunch;
  }

  public void setStartLunch(String startLunch) {
    this.startLunch = startLunch;
  }

  public String getEndLunch() {
    return endLunch;
  }

  public void setEndLunch(String endLunch) {
    this.endLunch = endLunch;
  }

  public boolean isShiftActive() {
    return shiftActive;
  }

  public void setShiftActive(boolean shiftActive) {
    this.shiftActive = shiftActive;
  }

  public boolean isBreakActive() {
    return breakActive;
  }

  public void setBreakActive(boolean breakActive) {
    this.breakActive = breakActive;
  }

  public boolean isLunchActive() {
    return lunchActive;
  }

  public void setLunchActive(boolean lunchActive) {
    this.lunchActive = lunchActive;
  }

  @Override
  public String toString() {
    return "Shift{" +
      "id=" + id +
      ", shiftDate='" + shiftDate + '\'' +
      ", startShift='" + startShift + '\'' +
      ", endShift='" + endShift + '\'' +
      ", startBreak='" + startBreak + '\'' +
      ", endBreak='" + endBreak + '\'' +
      ", startLunch='" + startLunch + '\'' +
      ", endLunch='" + endLunch + '\'' +
      ", shiftActive=" + shiftActive +
      ", breakActive=" + breakActive +
      ", lunchActive=" + lunchActive +
      ", user=" + user +
      '}';
  }
}

