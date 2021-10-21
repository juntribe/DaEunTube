package com.daeuntube.controller;


import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.dto.MainItemDTO;
import com.daeuntube.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${itemImgLocation}")
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping(value = "/")
    public String main(BoardSearchDTO boardSearchDTO, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Page<MainItemDTO> items = boardService.getMainItemPage(boardSearchDTO, pageable);


        model.addAttribute("items", items);
        model.addAttribute("boardSearchDTO", boardSearchDTO);
        model.addAttribute("maxPage", 5);

        return "main";
    }



}