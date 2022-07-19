package com.example.art.services.impl;

import com.example.art.model.MyUserDetails;
import com.example.art.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("fetching userDetails of username '{}'", username);
        return userRepository.findByEmail(username)
                .map(MyUserDetails::new)
                .orElseThrow( () -> new UsernameNotFoundException(username));
    }
}
