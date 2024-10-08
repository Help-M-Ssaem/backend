package com.example.mssaembackendv2.domain.board;

import java.util.List;

import com.example.mssaembackendv2.domain.board.dto.BoardRequestDto.*;
import com.example.mssaembackendv2.domain.board.dto.BoardResponseDto.*;
import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.common.dto.PageResponseDto;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    /**
     * HOT 게시물 더보기
     */
    @GetMapping("/boards/hot")
    public ResponseEntity<PageResponseDto<List<BoardSimpleInfo>>> findHotBoardList(
        @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(boardService.findHotBoardList(page, size));
    }

    /**
     * 홈 화면 조회 - HOT 게시물 4개
     */
    @GetMapping("/boards/home")
    public ResponseEntity<List<BoardSimpleInfo>> findHotBoardListForHome() {
        return ResponseEntity.ok(boardService.findHotBoardListForHome());
    }

    /**
     * 홈 화면 조회 - 3HOT
     */
    @GetMapping("/three-hot")
    public ResponseEntity<ThreeHotInfo> findThreeHotForHome() {
        return ResponseEntity.ok(boardService.findThreeHotForHome());
    }


    /**
     * 게시글 생성
     */
    //imgUrls : 업로드 했던 Urls 들
    //uploadImgUrls : 최종 업로드 된 imgUrls
    @PostMapping("/member/boards")
    public ResponseEntity<String> createBoard(@CurrentMember Member member,
        @RequestPart(value = "postBoardReq") PostBoardReq postBoardReq,
        @RequestPart(value = "image", required = false) List<String> imgUrls,
        @RequestPart(value = "uploadImage", required = false) List<String> uploadImgUrls) {
        return ResponseEntity.ok(
            boardService.createBoard(member, postBoardReq, imgUrls, uploadImgUrls));
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/member/boards/{id}")
    public ResponseEntity<String> modifyBoard(@CurrentMember Member member,
        @RequestPart(value = "patchBoardReq") PatchBoardReq patchBoardReq,
        @PathVariable(value = "id") Long id,
        @RequestPart(value = "image", required = false) List<String> imgUrls,
        @RequestPart(value = "uploadImage", required = false) List<String> uploadImgUrls) {
        return ResponseEntity.ok(
            boardService.modifyBoard(member, patchBoardReq, id, imgUrls, uploadImgUrls));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/member/boards/{id}")
    public ResponseEntity<String> deleteBoard(@CurrentMember Member member,
        @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(boardService.deleteBoard(member, id));
    }

    /**
     * 게시글 전체 조회 , 게시글 상세 조회시 boardId 입력 받아 현재 게시글 제외하고 전체 조회
     */
    @GetMapping("/boards")
    public ResponseEntity<PageResponseDto<List<BoardSimpleInfo>>> findBoards(
        @RequestParam(value = "page") int page, @RequestParam(value = "size") int size,
        @RequestParam(value = "boardId", required = false) Long boardId) {
        return ResponseEntity.ok(boardService.findBoards(page, size, boardId));
    }

    /**
     * Mbti 카테고리 별 게시글 전체 조회
     */
    @GetMapping("/boards/mbti")
    public ResponseEntity<PageResponseDto<List<BoardSimpleInfo>>> findBoardsByMbti(
        @RequestParam(value = "mbti", required = false) MbtiEnum mbti,
        @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(boardService.findBoardsByMbti(mbti, page, size));
    }

    /**
     * 특정 멤버별 게시글 전체 조회
     */
    @GetMapping("/boards/member")
    public ResponseEntity<PageResponseDto<List<BoardSimpleInfo>>> findBoardsById(
        @RequestParam(value = "memberId") Long memberId, @RequestParam(value = "page") int page,
        @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(boardService.findBoardsByMemberId(memberId, page, size));
    }

    /**
     * 게시글 상세조회
     */
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<GetBoardRes> findBoardById(@CurrentMember Member viewer,
        @PathVariable(value = "boardId") Long boardId) {
        return ResponseEntity.ok(boardService.findBoardById(viewer, boardId));
    }

    /**
     * 게시글 검색하기
     */
    @GetMapping("/boards/search")
    public ResponseEntity<PageResponseDto<List<BoardSimpleInfo>>> findBoardListByKeywordAndMbti(
        @RequestParam int searchType,
        @RequestParam String keyword,
        @RequestParam String strMbti,
        @RequestParam int page,
        @RequestParam int size) {
        return ResponseEntity.ok(
            boardService.findBoardListByKeywordAndMbti(searchType, keyword, strMbti, page, size));
    }

    //게시판 별 게시글 개수 조회
    @GetMapping("/boards/list")
    public ResponseEntity<BoardList> findBoardsList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }
}
