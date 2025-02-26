package com.example.demo.board.domain;

import com.example.demo.global.entity.BaseTimeEntity;
import com.example.demo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {

    // 키값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", updatable = false)
    private Long boardId;

    // 게시판 이름
    @Column
    private String title;

    // 게시판 설명
    @Column
    private String description;

    // 게시판 공지글 내용
    @Column(columnDefinition = "TEXT")
    private String pinned;

    // 게시판 주인(Member) foreign key
    @ManyToOne
    @JoinColumn(name = "member_id") // Builder에서 받은 Member 객체의 컬럼 중 member_id를 기준으로 join
    private Member owner;

    @Builder
    public Board (String title, String description, String pinned, Member owner) {
        Assert.hasLength(title, "Title must not be empty"); // 제목 값이 비어있을 경우 IllegalArgumentException을 발생시키도록 설정
        this.title = title;
        this.description = description;
        this.pinned = pinned;
        this.owner = owner;
    }

    // 게시판 주인 수정
    public void updateBoard(Member owner) {
        this.owner = owner;
    }

}
