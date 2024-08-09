package com.example.mssaembackendv2.global.config.security.auth;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.member.MemberRepository;
import com.example.mssaembackendv2.global.config.exception.BaseException;
import com.example.mssaembackendv2.global.config.exception.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByIdWithStatus(Long.parseLong(username))
                .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        return new PrincipalDetails(memberEntity);
    }
}


