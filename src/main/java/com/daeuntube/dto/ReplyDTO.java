package com.daeuntube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    private Long boardId;

    private LocalDate createdBy;

    private LocalDate  modifiedBy;
}
