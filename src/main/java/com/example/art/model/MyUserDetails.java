package com.example.art.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class MyUserDetails implements UserDetails {

    List<? extends GrantedAuthority> grantedAuthorities;
    String email;
    String password;
    boolean isActive;

    public MyUserDetails(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
        this.grantedAuthorities = generateAuthoritiesList(user.getRoles());
    }

    private List<? extends GrantedAuthority> generateAuthoritiesList(String roles){
        if(roles == null) return null;
        return Arrays.stream(roles.split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
