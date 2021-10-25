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

    @Value("${itemImgLocation}")
    private String itemImgLocation;


    private final BoardFileRepository boardFileRepository;

    private final FileService fileService;

    public void saveItemImg(BoardFile boardFile, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    itemImgFile.getBytes());
            imgUrl = itemImgLocation+"/" + imgName;
        }

        //이미지 정보 저장
        boardFile.updateItemImg(oriImgName, imgName, imgUrl);
        boardFileRepository.save(boardFile);
    }

    public void updateItemImg(Long id, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){
            BoardFile savedBoardFile = boardFileRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedBoardFile.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedBoardFile.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = itemImgLocation+"/" + imgName;
            savedBoardFile.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

}