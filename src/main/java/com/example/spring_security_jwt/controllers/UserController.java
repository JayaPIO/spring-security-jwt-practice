package com.example.spring_security_jwt.controllers;

import com.example.spring_security_jwt.dtos.UserDto;
import com.example.spring_security_jwt.models.Member;
import com.example.spring_security_jwt.services.UserService;
import com.example.spring_security_jwt.services.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class UserController {
    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Member> getMembers() {
        System.out.println("getting users");
        return memberServiceImpl.getMembers();
    }

    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable long id) {
        return userService.getUserByUserId(id);

    }
}
