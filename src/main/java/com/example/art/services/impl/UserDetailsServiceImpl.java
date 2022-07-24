package com.example.art.services.impl;

import com.example.art.model.MyUserDetails;
import com.example.art.repository.UserRepository;
import com.example.art.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
        MyUserDetails myUserDetails = userRepository.findByEmail(username)
                .map(MyUserDetails::new)
                .orElseThrow( () -> new UsernameNotFoundException(username));
        populateMDC(myUserDetails);
        return myUserDetails;
    }

    private void populateMDC(MyUserDetails myUserDetails) {
        log.info("populating userDetails in MDC");
        MDC.put(Constants.USER_ID, String.valueOf(myUserDetails.getUserId()));
        MDC.put(Constants.USER_EMAIL, myUserDetails.getUsername());
        MDC.put(Constants.USER_ROLES, myUserDetails.getRoles());
    }
}
