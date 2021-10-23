package com.daeuntube.entity;



import com.daeuntube.dto.BoardFormDTO;
import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Board extends BaseEntity {

    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String content;



    public void updateItem(BoardFormDTO boardFormDto){
        this.title = boardFormDto.getTitle();
        this.content = boardFormDto.getContent();
    }



}