 # 다은 튜브(DaEunTube) - 개인 프로젝트
 ## 목적
* 딸 영상편집 연습용으로 제작
* 어떠한 웹이던지 게시판은 필수요소

## 개발환경
* Tymeleaf
* Bootstrap
* Spring-Boot 2.5.5
* Spring Data JPA
* QueryDSL
* JDK 11
* MariaDB
* Gradle

# 제작 기간
2021.10.1 ~10.24

# ERD 설계
<img src="https://user-images.githubusercontent.com/63856867/138617284-2403373c-76c4-4ce0-bd80-dbd28d6749ff.png" width="700">

# 핵심 기능

<details>
<summary>설명 펼치기</summary>
<div markdown="1">

 ## 실행화면
<img src="https://user-images.githubusercontent.com/63856867/138623531-f270921e-8a13-4d8d-9b3a-a5bbec9e6d81.gif" width="900">
<img src="https://user-images.githubusercontent.com/63856867/138623542-426ac027-6064-4d66-a5f6-806f11db1a21.gif" width="900">
 <hr/>
 
 ## 회원가입 & 로그인

<img src="https://user-images.githubusercontent.com/63856867/138638206-17ca261e-a310-4eff-aa04-b60d35266b81.gif" width="900">

 Controller[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/a533eb605b86aa400a0ebc705180c43053109418/src/main/java/com/daeuntube/controller/MemberController.java#L41)

 
 Service[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/c957810d833bbed3137e83db2b9293c4fbf54b30/src/main/java/com/daeuntube/service/MemberServiceImpl.java#L18)
 
 <hr/>
 
 ## 게시판 CRUD
 
<img src="https://user-images.githubusercontent.com/63856867/138637904-8a9a975b-174e-4816-b08f-4bacc3c5a54d.gif" width="900">
<img src="https://user-images.githubusercontent.com/63856867/138637894-84dffe0a-ba16-4d74-bc18-47411c225500.gif" width="900">
 
 Controller[코드 바로가기]( https://github.com/juntribe/DaEunTube/blob/ab444b9e4bdad465b3e8b3608a6286bf906f045c/src/main/java/com/daeuntube/controller/BoardController.java#L32)
 
 Service[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/70e219a327a74f5e6c0340fcbfc66cc956edd16d/src/main/java/com/daeuntube/service/BoardService.java#L37)

 BoardFileService[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/ab444b9e4bdad465b3e8b3608a6286bf906f045c/src/main/java/com/daeuntube/service/BoardFileService.java#L26) 

 FileService[코드 바로가기]( https://github.com/juntribe/DaEunTube/blob/ab660de29d6e531e6133559113e789d449200ef5/src/main/java/com/daeuntube/service/FileService.java#L14)

 <hr/>
 ## 댓글 등록&삭제
<img src="https://user-images.githubusercontent.com/63856867/138637875-59e64f1c-98b7-4692-9e6f-7a8d9ca2c8c1.gif" width="900">
 
 Controller[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/e65ecd145b17026069ea4241f4203ea1b14641c1/src/main/java/com/daeuntube/controller/ReplyController.java#L22)

 Service[코드 바로가기](https://github.com/juntribe/DaEunTube/blob/e65ecd145b17026069ea4241f4203ea1b14641c1/src/main/java/com/daeuntube/service/ReplyServiceImpl.java#L22)


</div>

 
</details>
