package com.daeuntube.controller;


import com.daeuntube.config.SessionConstants;
import com.daeuntube.dto.BoardFormDTO;
import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.entity.Board;
import com.daeuntube.entity.Member;
import com.daeuntube.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/board/new")
    public String boardForm(Model model, @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        model.addAttribute("boardFormDTO", new BoardFormDTO());
        model.addAttribute("member", loginMember);
        return "board/boardForm";
    }

    @PostMapping(value = "/board/new")
    public String boardNew(@Valid BoardFormDTO boardFormDTO, BindingResult bindingResult,
                          Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList){

        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(boardImgFileList.get(0).isEmpty() && boardFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            boardService.saveBoard(boardFormDTO, boardImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "등록 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/board/update/{boardId}")
    public String modifyDtl(@PathVariable("boardId") Long boardId, Model model,@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){

        try {
            BoardFormDTO boardFormDTO = boardService.getImageDtl(boardId);
            model.addAttribute("boardFormDTO", boardFormDTO);
            model.addAttribute("member", loginMember);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 영상 입니다.");
            model.addAttribute("boardFormDTO", new BoardFormDTO());
            return "board/boardForm";
        }

        return "board/boardForm";
    }

    @PostMapping(value = "/board/update/{boardId}")
    public String updateBoard(@Valid BoardFormDTO boardFormDTO, BindingResult bindingResult,
                              @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(boardImgFileList.get(0).isEmpty() && boardFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째  이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            boardService.updateBoard(boardFormDTO, boardImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/board/list";
    }

    @GetMapping(value = {"/board/list", "/board/list/{page}"})
    public String boardManage(BoardSearchDTO boardSearchDTO,
                             @PathVariable("page") Optional<Integer> page,
                             Model model,@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Board> boards = boardService.getBoardListPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardSearchDTO", boardSearchDTO);
        model.addAttribute("maxPage", 10);
        model.addAttribute("member", loginMember);

        return "board/boardList";
    }

    @GetMapping(value = "/board/detail/{boardId}")
    public String boardDtl(Model model, @PathVariable("boardId") Long boardId,@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember){
        BoardFormDTO boardFormDTO = boardService.getImageDtl(boardId);
        model.addAttribute("board", boardFormDTO);
        model.addAttribute("member", loginMember);
        return "board/boardDetail";
    }

    @PostMapping("/board/delete")
    public String deleteBoard(Long boardId, RedirectAttributes redirectAttributes){

        boardService.removeBoard(boardId);
        redirectAttributes.addFlashAttribute("id",boardId);
        return "redirect:/board/list";
    }


}