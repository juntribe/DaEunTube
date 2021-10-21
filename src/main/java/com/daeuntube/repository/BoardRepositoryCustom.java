package com.daeuntube.repository;



import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.dto.MainItemDTO;
import com.daeuntube.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> getBoardPage(BoardSearchDTO boardSearchDto, Pageable pageable);

    Page<MainItemDTO> getMainItemPage(BoardSearchDTO boardSearchDto, Pageable pageable);

}