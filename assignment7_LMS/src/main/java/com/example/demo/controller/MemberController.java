package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;

    @PostMapping("/addMember")
    public ResponseEntity<MemberResponse> addMember(@RequestBody MemberRequest request){
        return new ResponseEntity<>(service.addMember(request), HttpStatus.CREATED);
    }

    @GetMapping("/showMember")
    public ResponseEntity<List<MemberResponse>>showMember(){
        return new ResponseEntity<>(service.showMember(), HttpStatus.OK);
    }
}