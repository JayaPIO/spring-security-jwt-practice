package com.example.spring_security_jwt.services;

import com.example.spring_security_jwt.repositories.UserRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

    @Component
    public class MyHealthService implements HealthIndicator {
        private final UserRepository userRepository;

        public MyHealthService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public Health health() {
            return checkDbHealth()?Health.up().withDetail("Database","reachable").build() :
                    Health.down().withDetail("Database"," not reachable").build();
        }
        private boolean checkDbHealth(){
            try{
                userRepository.count();
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
    }

