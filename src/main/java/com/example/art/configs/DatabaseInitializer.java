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
        User user = new User();
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(UserRole.ADMIN.name());
        user.setIsActive(true);

        if(!userRepository.existsByEmail(email))
        userRepository.save(user);


        addUsers();
    }

    private void addUsers() {

        User mod1 = new User();
        mod1.setRoles(UserRole.APP_MODERATOR.name());
        mod1.setEmail("moderator1@test.com");
        mod1.setPassword(passwordEncoder.encode("mod12345"));
        mod1.setIsActive(true);
        mod1.setMobile("1234387384");


        User mod2 = new User();
        mod2.setRoles(UserRole.BACKEND_MODERATOR.name());
        mod2.setEmail("moderator2@test.com");
        mod2.setPassword(passwordEncoder.encode("mod12345"));
        mod2.setIsActive(true);
        mod2.setMobile("1234387348");

        if(!userRepository.existsByEmail(mod1.getEmail()))
            userRepository.save(mod1);
        if(!userRepository.existsByEmail(mod2.getEmail()))
            userRepository.save(mod2);
        log.info("added two moderators");

    }

}
