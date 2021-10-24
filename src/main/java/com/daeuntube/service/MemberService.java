package com.daeuntube.service;

import com.daeuntube.dto.MemberDTO;
import com.daeuntube.entity.Member;

public interface MemberService {

    void joinUser(MemberDTO memberDTO);

    Member LoginUser(String loginId,String password);


    default Member dtoToEntity(MemberDTO dto){
         Member member = Member.builder()
                .loginId(dto.getLoginId())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
        return member;
    }

    default MemberDTO entityToDTO(Member member){
        MemberDTO memberDTO = MemberDTO.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .build();
        return memberDTO;
    }
}
