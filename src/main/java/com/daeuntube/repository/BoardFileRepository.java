package com.daeuntube.repository;


import com.daeuntube.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

    List<BoardFile> findByBoardIdOrderByIdAsc(Long boardId);

    BoardFile findByIdAndRepimgYn(Long id, String repimgYn);

    @Modifying
    @Query("delete from BoardFile f where f.board.id = :boardId")
    void deleteByBoardId(Long boardId);
}