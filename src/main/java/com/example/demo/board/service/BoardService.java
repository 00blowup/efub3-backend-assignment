package com.example.demo.board.service;

import com.example.demo.board.domain.Board;
import com.example.demo.board.dto.BoardCreateRequestDto;
import com.example.demo.board.dto.BoardModifyRequestDto;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Board createBoard(BoardCreateRequestDto requestDto) {
        if(existsByTitle(requestDto.getTitle())) {
            throw new IllegalArgumentException("이미 존재하는 게시판명입니다. " + requestDto.getTitle());
        }
        Member owner = memberRepository.findById(requestDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return boardRepository.save(
                Board.builder()
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .pinned(requestDto.getPinned())
                        .owner(owner)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    private boolean existsByTitle(String title) {
        return boardRepository.existsByTitle(title);
    }

    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));
    }

    public Long update(Long boardId, BoardModifyRequestDto requestDto) {
        Board board = findBoardById(boardId);
        board.updateBoard(memberRepository.findById(requestDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")));
        return boardId;
    }

    @Transactional
    public void deleteBoard(Long boardId, Long memberId) {
        Board board = boardRepository.findByBoardIdAndAndOwner_MemberId(boardId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
        boardRepository.delete(board);
    }
}
