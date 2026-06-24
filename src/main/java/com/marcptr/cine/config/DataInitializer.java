package com.marcptr.cine.config;

/*
*Crea el usuario Admin en la base de datos  
*si este no existe al arrancar springboot.
*/
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.marcptr.cine.document.AppSetting;
import com.marcptr.cine.document.User;
import com.marcptr.cine.model.enums.Role;
import com.marcptr.cine.repository.AppSettingRepository;
import com.marcptr.cine.repository.UserRepository;

@Configuration
public class DataInitializer {
    @Value("${admin.username}")
    private String username;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;
    @Value("${security.max_sessions}")
    private String maxSessions;

    @Bean
    CommandLineRunner initAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {

            boolean adminExists = userRepository.existsByRole(Role.ROLE_ADMIN);

            if (!adminExists) {
                var admin = User.create(
                        username,
                        email,
                        passwordEncoder.encode(password),
                        Role.ROLE_ADMIN);
                userRepository.save(admin);
            }
        };
    }

    @Bean
    CommandLineRunner initAppSetting(AppSettingRepository appSettingRepository) {
        return args -> {
            if (!appSettingRepository.existsByConfigKey("security.max_sessions")) {
                AppSetting setting = AppSetting.builder().configKey("security.max_sessions").configValue(maxSessions)
                        .build();
                appSettingRepository.save(setting);
            }
        };
    }
}