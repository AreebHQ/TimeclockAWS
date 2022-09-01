package com.example.TimeclockAWS.Services;


import com.example.TimeclockAWS.Entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;
  private final String id;
  private final String username;
  private final String email;
  @JsonIgnore
  private final String password;
  private final List<SimpleGrantedAuthority> authorities;


  public UserDetailsImpl(String id, String email, String password,
                         List<SimpleGrantedAuthority> authorities) {
    this.id = id;
    this.username = email;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }
  public static UserDetailsImpl build(User user)
  {

    List<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
    return new UserDetailsImpl(
      String.valueOf(user.getId()),
      user.getEmail(),
      user.getPassword(),
      authorities);

  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() { return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
