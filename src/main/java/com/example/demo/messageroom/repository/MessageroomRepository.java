package com.example.demo.messageroom.repository;

import com.example.demo.member.domain.Member;
import com.example.demo.messageroom.domain.Messageroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageroomRepository extends JpaRepository<Messageroom, Long> {
    List<Messageroom> findAllBySenderOrReceiver(Member sender, Member receiver);
}
