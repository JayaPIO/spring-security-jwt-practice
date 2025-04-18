package com.example.spring_security_jwt.repositories;

import com.example.spring_security_jwt.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
