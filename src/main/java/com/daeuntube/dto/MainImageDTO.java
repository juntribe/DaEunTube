package com.daeuntube.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainImageDTO {

    private Long id;

    private String title;

    private String content;

    private String imgUrl;


    @QueryProjection
    public MainImageDTO(Long id, String title, String content, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}