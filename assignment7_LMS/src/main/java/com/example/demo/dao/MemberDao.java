package com.example.demo.dao;

import java.util.List;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;

public interface MemberDao {

    public MemberResponse addMember(MemberRequest request);
    public List<MemberResponse> showMember();
}