package com.daeuntube.controller;


import com.daeuntube.dto.BoardFormDTO;
import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.entity.Board;
import com.daeuntube.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String itemForm(Model model){
        model.addAttribute("boardFormDTO", new BoardFormDTO());
        return "board/boardForm";
    }

    @PostMapping(value = "/board/new")
    public String itemNew(@Valid BoardFormDTO boardFormDTO, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(itemImgFileList.get(0).isEmpty() && boardFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            boardService.saveItem(boardFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "등록 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/board/update/{boardId}")
    public String itemDtl(@PathVariable("boardId") Long boardId, Model model){

        try {
            BoardFormDTO boardFormDTO = boardService.getItemDtl(boardId);
            model.addAttribute("boardFormDTO", boardFormDTO);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("boardFormDTO", new BoardFormDTO());
            return "board/boardForm";
        }

        return "board/boardForm";
    }

    @PostMapping(value = "/board/update/{boardId}")
    public String itemUpdate(@Valid BoardFormDTO boardFormDTO, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "board/boardForm";
        }

        if(itemImgFileList.get(0).isEmpty() && boardFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "board/boardForm";
        }

        try {
            boardService.updateItem(boardFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }

        return "redirect:/board/list";
    }

    @GetMapping(value = {"/board/list", "/board/list/{page}"})
    public String itemManage(BoardSearchDTO boardSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Board> items = boardService.getBoardListPage(boardSearchDTO, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", boardSearchDTO);
        model.addAttribute("maxPage", 10);

        return "board/boardList";
    }

    @GetMapping(value = "/board/detail/{boardId}")
    public String itemDtl(Model model, @PathVariable("boardId") Long boardId){
        BoardFormDTO boardFormDTO = boardService.getItemDtl(boardId);
        model.addAttribute("item", boardFormDTO);
        return "board/boardDetail";
    }

    @PostMapping("/board/delete")
    public String deleteBoard(Long boardId, RedirectAttributes redirectAttributes){

        boardService.removeBoard(boardId);
        redirectAttributes.addFlashAttribute("id",boardId);
        return "redirect:/board/list";
    }


}