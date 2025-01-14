package com.example.demo.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequestDto {
    @NotBlank(message = "필수 입력값입니다.")
    private String nickname;

    @Builder
    public MemberUpdateRequestDto (String nickname) {
        this.nickname = nickname;
    }
}
