package com.example.spring_security_jwt.services.impl;

import com.example.spring_security_jwt.models.Member;
import com.example.spring_security_jwt.services.MemberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private final List<Member> members = new ArrayList<>();

    public MemberServiceImpl() {
        members.add(new Member(UUID.randomUUID().toString(), "jaya", "j@gmail.com"));
        members.add(new Member(UUID.randomUUID().toString(), "riya", "r@gmail.com"));
        members.add(new Member(UUID.randomUUID().toString(), "Sia", "S@gmail.com"));
        members.add(new Member(UUID.randomUUID().toString(), "ram", "ram@gmail.com"));
    }

    /**
     * displaying list of members on frontend
     *
     * @return List of members
     */
    public List<Member> getMembers() {
        return members;
    }
}
