package com.daeuntube.dto;



import com.daeuntube.entity.BoardFile;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class BoardFileDTO {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardFileDTO of(BoardFile boardFile) {
        return modelMapper.map(boardFile, BoardFileDTO.class);
    }

}