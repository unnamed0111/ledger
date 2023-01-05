package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import com.portfolio.ledger.dto.MemberJoinDTO;
import com.portfolio.ledger.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
        String mid = memberJoinDTO.getMid();

        boolean exist = memberRepository.existsByMid(mid);

        if(exist) throw new MidExistException(); // 이미 아이디가 존재하면

        Member member = modelMapper.map(memberJoinDTO, Member.class);

        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);

        log.info("=================ENCODED PASSWORD=================");
        log.info(member);

        memberRepository.save(member);
    }
}
