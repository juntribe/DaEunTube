package com.daeuntube.service;




import com.daeuntube.entity.BoardFile;
import com.daeuntube.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardFileService {

    @Value("${imgLocation}")
    private String imgLocation;


    private final BoardFileRepository boardFileRepository;

    private final FileService fileService;

    public void saveBoardImg(BoardFile boardFile, MultipartFile boardImgFile) throws Exception{
        String oriImgName = boardImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(imgLocation, oriImgName,
                    boardImgFile.getBytes());
            imgUrl = imgLocation+"/" + imgName;
        }

        //이미지 정보 저장
        boardFile.updateBoardImg(oriImgName, imgName, imgUrl);
        boardFileRepository.save(boardFile);
    }

    public void updateBoardImg(Long id, MultipartFile boardImgFile) throws Exception{
        if(!boardImgFile.isEmpty()){
            BoardFile savedBoardFile = boardFileRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedBoardFile.getImgName())) {
                fileService.deleteFile(imgLocation+"/"+
                        savedBoardFile.getImgName());
            }

            String oriImgName = boardImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(imgLocation, oriImgName, boardImgFile.getBytes());
            String imgUrl = imgLocation+"/" + imgName;
            savedBoardFile.updateBoardImg(oriImgName, imgName, imgUrl);
        }
    }

}