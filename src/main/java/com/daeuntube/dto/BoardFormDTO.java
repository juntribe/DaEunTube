package com.daeuntube.dto;


import com.daeuntube.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardFormDTO {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotNull(message = "내용은 필수 입력 값입니다.")
    private String content;


    private List<BoardFileDTO> boardFileDTOList = new ArrayList<>();

    private List<Long> boardFileIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return modelMapper.map(this, Board.class);
    }

    public static BoardFormDTO of(Board board){
        return modelMapper.map(board, BoardFormDTO.class);
    }

}