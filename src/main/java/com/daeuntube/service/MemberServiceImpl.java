package com.daeuntube.service;

import com.daeuntube.dto.MemberDTO;
import com.daeuntube.entity.Member;
import com.daeuntube.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {


   private final MemberRepository memberRepository;

    @Override
    public void joinUser(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        validateDuplicateMember(member);
        memberRepository.save(member);
    }
    // Id 중복 체크
    private void validateDuplicateMember(Member member){
        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    // Id,pwd 유효성 체크
    @Override
    public Member LoginUser(String loginId, String password) {
        return memberRepository.findByLoginId(loginId).filter(m->
                m.getPassword().equals(password)).orElse(null);
    }
}
