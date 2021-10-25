package com.daeuntube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private LocalDate createdBy;

    private LocalDate  modifiedBy;

}