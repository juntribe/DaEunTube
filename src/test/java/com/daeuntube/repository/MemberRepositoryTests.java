package com.daeuntube.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .id("user" +i)
                    .password("1111")
                    .name("User" + i)
                    .build();
            memberRepository.save(member);
        });
    }
}
