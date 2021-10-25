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



    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("boardId") Long boardId) {

        log.info("id.." + boardId);

        return new ResponseEntity<>(replyService.getList(boardId), HttpStatus.OK);
    }



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


}
