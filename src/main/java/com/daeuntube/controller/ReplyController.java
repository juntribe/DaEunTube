package com.daeuntube.controller;


import com.daeuntube.dto.ReplyDTO;
import com.daeuntube.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService; // 자동주입을 위해 final

    //@RestController 의 경우 모든 메서드의 리턴 타입은 기본으로 JSON을 사용합니다.
    // 메서드의 반환 타입은 ResponseEntity라는 객체를 이용하는데 이를 이요하면 HTTP의 상태코드 등을 전달할수있습니다

    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("boardId") Long boardId) {

        log.info("id.." + boardId);

        return new ResponseEntity<>(replyService.getList(boardId), HttpStatus.OK);
    }

    // register()에서는 특별하게 @RequestBody 라는 어노테이션을 사용합니다.
    // @RequestBody는 JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해 주는 역할을 담당합니다.
    // 때문에 개발 시에는 별도으 ㅣ처리 없이도 JSON 데이터를 특정 타입으 ㅣ객체로 변환해서 처리할수 있습니다.

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {

        log.info("RNO" + rno);
        replyService.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
