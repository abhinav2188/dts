package com.example.art.configs;

import com.example.art.model.User;
import com.example.art.model.enums.UserRole;
import com.example.art.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseInitializer implements ApplicationRunner {

    @Value("${EMAIL}")
    private String email;

    @Value("${PASSWORD}")
    private String password;

    @Value("${MOBILE}")
    private String mobile;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("adding default admin user");
        if(userRepository.existsByEmail(email)) return;
        User user = new User();
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(UserRole.ADMIN.name());
        user.setIsActive(true);
        userRepository.save(user);
    }

}
