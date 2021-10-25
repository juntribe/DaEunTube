package com.daeuntube.controller;


import com.daeuntube.config.SessionConstants;
import com.daeuntube.dto.BoardSearchDTO;
import com.daeuntube.dto.MainImageDTO;
import com.daeuntube.entity.Member;
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
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${itemImgLocation}")
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping(value = "/")
    public String main(BoardSearchDTO boardSearchDTO,
                       Optional<Integer> page, @SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember,
                        Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Page<MainImageDTO> imageFile = boardService.getMainImagePage(boardSearchDTO, pageable);


        model.addAttribute("images", imageFile);
        model.addAttribute("boardSearchDTO", boardSearchDTO);
        model.addAttribute("maxPage", 5);
        model.addAttribute("member",loginMember);

        return "main";
    }



}