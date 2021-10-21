package com.daeuntube.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDTO {

    private Long id;

    private String title;

    private String content;

    private String imgUrl;


    @QueryProjection
    public MainItemDTO(Long id, String title, String content, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}