package com.daeuntube.service;



import com.daeuntube.dto.BoardFileDTO;
import com.daeuntube.dto.BoardFormDTO;
import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.dto.MainItemDTO;
import com.daeuntube.entity.Board;
import com.daeuntube.entity.BoardFile;
import com.daeuntube.repository.BoardFileRepository;
import com.daeuntube.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardFileService boardFileService;

    private final BoardFileRepository boardFileRepository;

    public void saveBoard(BoardFormDTO boardFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Board board = boardFormDto.createBoard();
        boardRepository.save(board);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            BoardFile boardFile = new BoardFile();
            boardFile.setBoard(board);

            if(i == 0)
                boardFile.setRepimgYn("Y");
            else
                boardFile.setRepimgYn("N");

            boardFileService.saveItemImg(boardFile, itemImgFileList.get(i));
        }

    }

    @Transactional(readOnly = true)
    public BoardFormDTO getItemDtl(Long boardId){
        List<BoardFile> boardFileList = boardFileRepository.findByBoardIdOrderByIdAsc(boardId);
        List<BoardFileDTO> boardFileDTOList = new ArrayList<>();
        for (BoardFile boardFile : boardFileList) {
            BoardFileDTO boardFileDTO = BoardFileDTO.of(boardFile);
            boardFileDTOList.add(boardFileDTO);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        BoardFormDTO boardFormDTO = BoardFormDTO.of(board);
        boardFormDTO.setBoardFileDTOList(boardFileDTOList);
        return boardFormDTO;
    }

    public void updateBoard(BoardFormDTO boardFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 수정
        Board board = boardRepository.findById(boardFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardFormDto);
        List<Long> itemImgIds = boardFormDto.getBoardFileIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            boardFileService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardListPage(BoardSearchDTO boardSearchDto, Pageable pageable){
        return boardRepository.getBoardPage(boardSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDTO> getMainItemPage(BoardSearchDTO boardSearchDto, Pageable pageable){
        return boardRepository.getMainItemPage(boardSearchDto, pageable);
    }
    @Transactional
    public void removeBoard(Long boardId){

        boardFileRepository.deleteByBoardId(boardId);
        boardRepository.deleteById(boardId);
    }

}