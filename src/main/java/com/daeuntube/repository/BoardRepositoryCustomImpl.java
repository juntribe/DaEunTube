package com.daeuntube.repository;


import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.dto.MainItemDTO;
import com.daeuntube.dto.QMainItemDTO;
import com.daeuntube.entity.Board;
import com.daeuntube.entity.QBoard;
import com.daeuntube.entity.QBoardFile;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;


public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }




    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("title", searchBy)){
            return QBoard.board.title.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Board> getBoardPage(BoardSearchDTO boardSearchDto, Pageable pageable) {

        QueryResults<Board> results = queryFactory
                .selectFrom(QBoard.board)
                .where(searchByLike(boardSearchDto.getSearchBy(),
                                boardSearchDto.getSearchQuery()))
                .orderBy(QBoard.board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Board> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QBoard.board.title.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDTO> getMainItemPage(BoardSearchDTO boardSearchDto, Pageable pageable) {
        QBoard board = QBoard.board;
        QBoardFile boardFile = QBoardFile.boardFile;

        QueryResults<MainItemDTO> results = queryFactory
                .select(
                        new QMainItemDTO(
                                board.id,
                                board.title,
                                board.content,
                                boardFile.imgUrl
                        )
                )
                .from(boardFile)
                .join(boardFile.board, board)
                .where(boardFile.repimgYn.eq("Y"))
                .where(itemNmLike(boardSearchDto.getSearchQuery()))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

}