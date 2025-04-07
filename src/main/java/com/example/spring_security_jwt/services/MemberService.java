package com.example.spring_security_jwt.services;

import com.example.spring_security_jwt.models.Member;

import java.util.List;

public interface MemberService {
    List<Member> getMembers();
}
