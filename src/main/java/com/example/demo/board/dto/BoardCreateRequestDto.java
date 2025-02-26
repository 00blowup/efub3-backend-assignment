package com.example.demo.board.dto;

import com.example.demo.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

// 새 게시판 생성 요청을 보낼 때 사용되는 DTO
@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank(message = "게시판명은 필수입니다.")
    private String title;

    private String description;

    private String pinned;

    private Long ownerId;

}
