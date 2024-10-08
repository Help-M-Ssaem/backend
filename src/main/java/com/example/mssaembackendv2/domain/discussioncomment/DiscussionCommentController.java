package com.example.mssaembackendv2.domain.discussioncomment;

import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.global.common.CommentService;
import com.example.mssaembackendv2.global.common.CommentTypeEnum;
import com.example.mssaembackendv2.global.common.dto.CommentDto.GetCommentsByMemberRes;
import com.example.mssaembackendv2.global.common.dto.CommentDto.GetCommentsRes;
import com.example.mssaembackendv2.global.common.dto.CommentDto.PostCommentReq;
import com.example.mssaembackendv2.global.common.dto.PageResponseDto;
import com.example.mssaembackendv2.global.config.security.auth.CurrentMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiscussionCommentController {

    private final CommentService commentService;

    //토론글 상세 조회시 댓글 조회
    @GetMapping("/discussions/{discussionId}/comments")
    public ResponseEntity<PageResponseDto<List<GetCommentsRes>>> findDiscussionComments(
        @CurrentMember Member member, @PathVariable(value = "discussionId") Long discussionId,
        @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(
            commentService.findCommentsByPostId(member, discussionId, page, size,
                CommentTypeEnum.DISCUSSION));
    }

    //토론글 상세 조회시 베스트 댓글 3개 조회
    @GetMapping("/discussions/{discussionId}/comments/best")
    public ResponseEntity<List<GetCommentsRes>> findDiscussionCommentBestListByDiscussionId(
        @CurrentMember Member member, @PathVariable(value = "discussionId") Long discussionId) {
        return ResponseEntity.ok(commentService.findBestCommentsByPostId(member, discussionId,
            CommentTypeEnum.DISCUSSION));
    }

    //댓글 작성, 댓글 작성 시 @RequestParam 으로 commentId 값 받으면 대댓글 작성
    @PostMapping("/member/discussions/{discussionId}/comments")
    public ResponseEntity<String> createDiscussionComment(@CurrentMember Member member,
        @PathVariable(value = "discussionId") Long discussionId,
        @RequestPart(value = "postDiscussionCommentReq") PostCommentReq postCommentReq,
        @RequestParam(value = "commentId", required = false) Long commentId) {
        return ResponseEntity.ok(
            commentService.createComment(member, discussionId,
                postCommentReq, commentId, CommentTypeEnum.DISCUSSION, commentId != null));
    }

    //댓글 삭제
    @DeleteMapping("/member/discussions/{discussionId}/comments/{commentId}")
    public ResponseEntity<String> deleteDiscussionComment(@CurrentMember Member member,
        @PathVariable(value = "discussionId") Long discussionId,
        @PathVariable(value = "commentId") Long commentId) {
        return ResponseEntity.ok(
            commentService.deleteComment(member, discussionId, commentId,
                CommentTypeEnum.DISCUSSION));
    }

    //특정 멤버별 토론글 댓글 보기
    @GetMapping("/discussions/comments")
    public ResponseEntity<PageResponseDto<List<GetCommentsByMemberRes>>> findDiscussionCommentListByMemberId(
        @RequestParam(value = "memberId") Long memberId, @RequestParam(value = "page") int page,
        @RequestParam(value = "size") int size, @CurrentMember Member member) {
        return ResponseEntity.ok(
            commentService.findCommentsByMember(memberId, page, size,
                member, CommentTypeEnum.DISCUSSION));
    }
}