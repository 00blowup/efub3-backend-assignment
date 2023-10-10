package com.example.demo.messageroom.controller;

import com.example.demo.messageroom.domain.Messageroom;
import com.example.demo.messageroom.dto.MessageroomCreateRequestDto;
import com.example.demo.messageroom.dto.MessageroomListResponseDto;
import com.example.demo.messageroom.dto.MessageroomResponseDto;
import com.example.demo.messageroom.service.MessageroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messagerooms")
public class MessageroomController {

    private final MessageroomService messageroomService;

    // 쪽지방 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageroomResponseDto createRoom(@RequestBody MessageroomCreateRequestDto requestDto) {
        Long id = messageroomService.createRoom(requestDto);
        Messageroom room = messageroomService.findById(id);
        return MessageroomResponseDto.of(room);
    }

    // 특정 사용자가 참여 중인 모든 쪽지방의 목록 조회
    @GetMapping("/member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public MessageroomListResponseDto findRoomList(@PathVariable Long memberId) {
        List<Messageroom> roomList = messageroomService.findAllBySenderOrReceiver(memberId);
        return new MessageroomListResponseDto(roomList);
    }


}
