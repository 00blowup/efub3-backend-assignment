package com.example.demo.messageroom.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import com.example.demo.messageroom.domain.Messageroom;
import com.example.demo.messageroom.dto.MessageroomCreateRequestDto;
import com.example.demo.messageroom.repository.MessageroomRepository;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageroomService {

    private final MessageroomRepository messageroomRepository;
    private final MemberService memberService;
    private final PostService postService;

    public Long createRoom(MessageroomCreateRequestDto requestDto) {
        Member sender = findMemberById(requestDto.getSenderId());
        Member receiver = findMemberById(requestDto.getReceiverId());
        Post post = postService.findPostById(requestDto.getPostId());
        Messageroom room = messageroomRepository.save(requestDto.toEntity(sender, receiver, post));
        return room.getMessageroomId();
    }

    public Messageroom findById(Long id) {
        return findMessageroomById(id);
    }

    public List<Messageroom> findAllBySenderOrReceiver(Long memberId) {
        Member member = findMemberById(memberId);
        return findBySenderOrReceiver(member);
    }

    @Transactional(readOnly = true)
    public Messageroom findMessageroomById(Long id) {
        return messageroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 쪽지방을 찾을 수 없습니다. ID = " + id));
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberService.findMemberById(id);
    }

    @Transactional(readOnly = true)
    public List<Messageroom> findBySenderOrReceiver (Member member) {
        return messageroomRepository.findAllBySenderOrReceiver(member, member);
    }

}
