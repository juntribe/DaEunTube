package com.daeuntube.service;


import com.daeuntube.dto.ReplyDTO;
import com.daeuntube.entity.Board;
import com.daeuntube.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); // 댓글의 등록

    List<ReplyDTO> getList(Long bno); // 특정 게시물의 댓글 목록


    void remove(Long rno); // 댓글 삭제

    // ReplyDTo를 Reply 객체로 변환 Board객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO replyDTO) {

        Board board = Board.builder()
                .id(replyDTO.getBoardId()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    // Reply 객체를 replyDTO로 변환 Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .createdBy(reply.getCreatedBy())
                .modifiedBy(reply.getModifiedBy())
                .build();

        return dto;
    }

}
