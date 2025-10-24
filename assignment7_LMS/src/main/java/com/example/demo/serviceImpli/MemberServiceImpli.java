package com.example.demo.serviceImpli;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDao;
import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.MemberService;

@Service
public class MemberServiceImpli implements MemberService {

    @Autowired
    private MemberDao dao;

    @Override
    public MemberResponse addMember(MemberRequest request) {
        return dao.addMember(request);
    }

    @Override
    public List<MemberResponse> showMember() {
        // TODO Auto-generated method stub
        return dao.showMember();
    }

}