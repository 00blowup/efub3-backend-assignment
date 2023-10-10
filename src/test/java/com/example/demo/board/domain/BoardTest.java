package com.example.demo.board.domain;

import com.example.demo.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardTest {

    @Autowired
    private TestEntityManager entityManager;

    // 게시판명이 입력되지 않았을 경우 의도한 대로 오류를 발생시킴을 확인
    @Test
    public void createWhenTitleIsNullShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Board(null, "desc", "pinned", null));
    }

    // 데이터 저장이 제대로 되는지 테스트. Member와의 Foreign key 의존관계로 인한 오류를 피하기 위해 owner를 null로 입력하였음.
    @Test
    public void saveShouldPersistData() {
        // given
        final String EXPECTED_TITLE = "title";

        // when
        Board board = entityManager.persistFlushFind(new Board(EXPECTED_TITLE, "desc", "pinned", null));

        // then
        assertThat(board.getTitle()).isEqualTo(EXPECTED_TITLE);
    }
}