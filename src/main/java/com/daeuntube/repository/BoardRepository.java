package com.daeuntube.repository;



import com.daeuntube.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,
        QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {

    List<Board> findByTitle(String title);

    List<Board> findByTitleOrContent(String title, String content);


    @Query("select b from Board b where b.content like " +
            "%:content% order by b.createdBy desc")
    List<Board> findByBoardDetail(@Param("content") String content);

    @Query(value="select * from board i where i.content like " +
            "%:content% order by i.createdBy desc", nativeQuery = true)
    List<Board> findByBoardDetailByNative(@Param("content") String content);

}