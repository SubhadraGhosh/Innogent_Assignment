package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;

public interface MemberService {

    public MemberResponse addMember(MemberRequest request);
    public List<MemberResponse> showMember();
}